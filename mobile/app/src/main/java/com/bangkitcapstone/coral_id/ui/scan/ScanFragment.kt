package com.bangkitcapstone.coral_id.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentScanBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanFragment : Fragment(), View.OnClickListener {

    private var imageCapture: ImageCapture? = null
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding
    private var flash = false
    private lateinit var imageFile: Uri

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
        }

        binding?.apply {
            mtoolbarScan.setNavigationOnClickListener {
                Toast.makeText(activity, "Close scan fragment", Toast.LENGTH_SHORT).show()
            }
            btnFlash.setOnClickListener(this@ScanFragment)
            btnCapture.setOnClickListener(this@ScanFragment)
            btnStorage.setOnClickListener(this@ScanFragment)
            btnConfirmNo.setOnClickListener(this@ScanFragment)
            btnConfirmYes.setOnClickListener(this@ScanFragment)
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SELECT_SUCCESS_CODE && requestCode == SELECT_PICTURE_CODE) {
            Toast.makeText(context, "Capture success", Toast.LENGTH_SHORT).show()
            Log.d(TAG, data?.data.toString())
            findNavController().navigate(R.id.action_scanFragment_to_resultFragment)
            showConfirmation(true)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(context, "Permission rejected by the user", Toast.LENGTH_SHORT)
                    .show()
                activity?.finish()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_flash -> {
                flashConfig(flash)
            }
            R.id.btn_capture -> {
                takePhoto()
            }
            R.id.btn_storage -> {
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also {
                    it.setType("image/*")
                    startActivityForResult(it, SELECT_PICTURE_CODE)
                }
            }
            R.id.btn_confirm_no -> {
                showConfirmation(false)
                val deleted: Boolean = File(imageFile.toString().replace("file://", "")).delete()
                Log.d(TAG, "onClick: $deleted")
            }
            R.id.btn_confirm_yes -> {
                findNavController().navigate(R.id.action_scanFragment_to_resultFragment)
            }
        }
    }

    private fun flashConfig(state: Boolean) {
        with(binding) {
            if (state) {
                flash = false
                this!!.btnFlash.setImageResource(R.drawable.ic_flash_off)
                Toast.makeText(context, "Turn off flash", Toast.LENGTH_SHORT).show()
            } else {
                flash = true
                this!!.btnFlash.setImageResource(R.drawable.ic_flash_on)
                Toast.makeText(context, "Turn on flash", Toast.LENGTH_SHORT).show()
            }
            startCamera()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Toast.makeText(context, "Capture Success", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Capture Success: $savedUri")
                    binding?.imagePreview?.setImageURI(savedUri)
                    imageFile = savedUri
                    showConfirmation(true)
                }

                override fun onError(e: ImageCaptureException) {
                    Log.d(TAG, "Capture failed: ${e.message}", e)
                }

            }
        )
    }

    private fun startCamera() {
        val cameraProvideFuture = context?.let { ProcessCameraProvider.getInstance(it) }

        cameraProvideFuture?.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProvideFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding?.cameraPreview?.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()
            // check flash status
            Log.d(TAG, if (flash) "Flash on" else "Flash off")

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val cam =
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                cam.cameraControl.enableTorch(flash)
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity().baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }

    private fun showConfirmation(state: Boolean) {
        with(binding!!) {
            if (state) {
                imagePreview.visibility = View.VISIBLE
                confirmCard.visibility = View.VISIBLE
                captureCard.visibility = View.GONE
                cameraPreview.visibility = View.GONE
                btnFlash.visibility = View.GONE
                cameraExecutor.shutdown()
            } else {
                imagePreview.visibility = View.GONE
                confirmCard.visibility = View.GONE
                captureCard.visibility = View.VISIBLE
                cameraPreview.visibility = View.VISIBLE
                btnFlash.visibility = View.VISIBLE
                startCamera()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        _binding = null
    }

    companion object {
        private const val TAG = "Scan Fragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val SELECT_PICTURE_CODE = 100
        private const val SELECT_SUCCESS_CODE = -1
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}