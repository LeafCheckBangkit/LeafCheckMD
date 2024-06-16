package com.example.leafcheck.ui.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.databinding.ActivityResultBinding
import com.example.leafcheck.utils.ImageClassifierHelper

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var imageClassifierHelper: ImageClassifierHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageString = intent.getStringExtra(IMAGE_URI)
        if (imageString != null) {
            val imageUri = Uri.parse(imageString)
            displayImage(imageUri)

            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        Log.d(TAG, "Error: $error")
                        showToast("Error: $error")
                    }

                    override fun onResults(result: FloatArray?, inferenceTime: Long) {
                        result?.let { showResult(it) }
                    }
                }
            )
            imageClassifierHelper?.classifyStaticImage(imageUri)
        } else {
            Log.e(TAG, "No Image Provided")
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imageClassifierHelper = null
    }

    private fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(result: FloatArray) {
        // Menentukan kondisi dengan probabilitas tertinggi
        val maxIndex = result.indices.maxByOrNull { result[it] } ?: -1

        // Pesan berdasarkan kondisi dengan probabilitas tertinggi
        val message = when (maxIndex) {
            0 -> "Keterangan: Pohon apel anda sehat"
            1 -> "Keterangan: Pohon apel anda sakit"
            2 -> "Keterangan: Pohon jeruk anda sehat"
            3 -> "Keterangan: Pohon jeruk anda sakit"
            4 -> "Keterangan: Pohon mangga anda sehat"
            5 -> "Keterangan: Pohon mangga anda sakit"
            else -> "Keterangan: Tidak dapat menentukan kondisi"
        }

        // Menampilkan pesan
        binding.resultText.text = message
        Log.d(TAG, "Result: $message")
    }


    private fun displayImage(uri: Uri) {
        Log.d(TAG, "Display Image: $uri")
        binding.resultImage.setImageURI(uri)
    }

    companion object {
        const val IMAGE_URI = "img_uri"
        const val TAG = "imagePicker"
    }
}
