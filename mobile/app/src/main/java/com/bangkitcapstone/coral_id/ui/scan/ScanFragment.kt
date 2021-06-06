package com.bangkitcapstone.coral_id.ui.scan

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bangkitcapstone.coral_id.R
import com.bangkitcapstone.coral_id.databinding.FragmentScanBinding
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanFragment : Fragment(), View.OnClickListener {

    private var imageCapture: ImageCapture? = null
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding
    private var flash = false
    private var imageFile: Uri? = null
    private var realFile: Uri? = null
    private var isFromStorage = false

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
                activity?.onBackPressed()
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
            isFromStorage = true
            binding?.imagePreview?.setImageURI(data?.data)
            realFile = data?.data
            imageFile = createCopyAndReturnRealPath(requireContext(), data?.data!!)?.toUri()
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
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.setType("image/*")
                    startActivityForResult(it, SELECT_PICTURE_CODE)
                }
            }
            R.id.btn_confirm_no -> {
                showConfirmation(false)
                if (imageFile != null) {
                    if (!isFromStorage) {
                        File(imageFile?.path.toString()).delete()
                        File(realFile?.path.toString()).delete()
                    } else {
                        File(imageFile?.path.toString()).delete()
                    }
                }
            }
            R.id.btn_confirm_yes -> {
                val bundle = bundleOf(
                    "uri" to imageFile.toString()
                )
                findNavController().navigate(R.id.action_scanFragment_to_resultFragment, bundle)
            }
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
                    binding?.imagePreview?.setImageURI(savedUri)
                    realFile = savedUri
                    imageFile = createCopyAndReturnRealPath(requireContext(), savedUri)?.toUri()
                    isFromStorage = false
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

    private fun createCopyAndReturnRealPath(
        context: Context, uri: Uri
    ): String? {
        val contentResolver: ContentResolver = context.getContentResolver() ?: return null

        val file = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null

            val outputStream: OutputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also {
                    len = it
                } > 0) outputStream.write(buf, 0, len)
            outputStream.close()
            inputStream.close()

        } catch (ignore: IOException) {
            return null
        }
        if (file.length().div(1024) > MAX_FILE) {
            imageFile = file.toUri()
            compresImage()
        }
        return file.absolutePath
    }

    private fun compresImage() {
        imageFile?.toFile()?.let {
            lifecycleScope.launch {
                Log.d("lihat file", imageFile.toString())
                Compressor.compress(requireContext(), it) {
                    resolution(600, 600)
                    quality(100)
                    size(350000) // 600 x 600
                    destination(it)
                }
            }
        } ?: Log.e(TAG, "eror compress")
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
        private const val MAX_FILE = 350.000
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}