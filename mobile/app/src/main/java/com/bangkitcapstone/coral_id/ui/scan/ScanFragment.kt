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

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            btnPickImage.setOnClickListener(this@ScanFragment)
            btnStorage.setOnClickListener(this@ScanFragment)
        }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
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
            R.id.btn_pick_image -> {
                takePhoto()
                // TODO: 5/24/2021 Find solution for this, need delay before intent to next fragment 
                //findNavController().navigate(R.id.action_scanFragment_to_resultFragment)
            }
            R.id.btn_storage -> {
                // TODO: 5/24/2021 Still confuse how this work 
                // TODO: 5/24/2021 Re-design fragment_scan.xml and test Flash
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also {
                    it.setType("image/*")
                    startActivity(it)
                }
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
                    val msg = "Capture success: $savedUri"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
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
                .setFlashMode(
                    if (flash) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
                )
                .build()
            // check flash status
            Log.d(TAG, if (flash) "Flash on" else "Flash off")

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                val cam =
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
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

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        _binding = null
    }

    companion object {
        private const val TAG = "Scan Fragment"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}