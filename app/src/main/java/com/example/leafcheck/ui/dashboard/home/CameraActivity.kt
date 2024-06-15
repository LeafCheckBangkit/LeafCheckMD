package com.example.leafcheck.ui.dashboard.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.leafcheck.R
import com.example.leafcheck.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initGallery()
        binding.let {
//            it.btnShutter.setOnClickListener {
//                takePhoto()
//            }
            it.btnGallery.setOnClickListener {
                startGallery()
            }
            it.btnBack.setOnClickListener {
                onBackPressed()
            }
        }
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 720))
                .build()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().setTargetResolution(Size(480, 720)).build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture, imageAnalysis
                )
            } catch (e: Exception) {
            }
        }, ContextCompat.getMainExecutor(this))
    }

//    private fun initGallery() {
//        openGalleryLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                Log.i("TEST_GALERY", "Galeri berhasil dipilih")
//                val selectedImg: Uri = result.data?.data as Uri
//                val myFile = Helper.uriToFile(selectedImg, this@CameraActivity)
//                val intent = Intent(this@CameraActivity, NewStoryActivity::class.java)
//                intent.putExtra(NewStoryActivity.EXTRA_PHOTO_RESULT, myFile)
//                intent.putExtra(
//                    NewStoryActivity.EXTRA_CAMERA_MODE,
//                    cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                )
//                this@CameraActivity.finish()
//                startActivity(intent)
//            }
//        }
//    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        openGalleryLauncher.launch(chooser)
    }

//    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
//        val photoFile = Helper.createFile(application)
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Helper.showDialogInfo(
//                        this@CameraActivity,
//                        "${getString(androidx.camera.core.R.string.UI_error_camera_take_photo)} : ${exc.message}"
//                    )
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//
//                    val intent = Intent(this@CameraActivity, NewStoryActivity::class.java)
//                    intent.putExtra(NewStoryActivity.EXTRA_PHOTO_RESULT, photoFile)
//                    intent.putExtra(
//                        NewStoryActivity.EXTRA_CAMERA_MODE,
//                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                    )
//                    this@CameraActivity.finish()
//                    startActivity(intent)
//                }
//            }
//        )
//    }
}