package com.example.leafcheck.ui.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.databinding.ActivityResultBinding
import com.example.leafcheck.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

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

                    override fun onResults(result: List<Classifications>?, inferenceTime: Long) {
                        result?.let { showResult(it, imageString) }
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
        // Pastikan sumber daya ditutup
        imageClassifierHelper = null
    }

    private fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(result: List<Classifications>, uri: String) {
        val topResult = result[0]
        val label = topResult.categories[0].label
        val score = topResult.categories[0].score

        // Menampilkan hasil dalam bentuk teks
        val resultText = "$label (${(score * 100).toInt()}%)"
        binding.resultText.text = resultText

        // Menampilkan label dan score di logcat
        Log.d(TAG, "Result: $resultText")
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
