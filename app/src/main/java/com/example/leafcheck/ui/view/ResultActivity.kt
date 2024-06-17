package com.example.leafcheck.ui.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.leafcheck.databinding.ActivityResultBinding
import com.example.leafcheck.utils.ImageClassifierHelper
import java.io.BufferedReader
import java.io.InputStreamReader

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var imageClassifierHelper: ImageClassifierHelper? = null
    private lateinit var labels: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load labels from assets/labels.txt
        labels = loadLabels()

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

    private fun loadLabels(): List<String> {
        val labels = mutableListOf<String>()
        try {
            val reader = BufferedReader(InputStreamReader(assets.open("labels.txt")))
            reader.useLines { lines ->
                lines.forEach { line ->
                    Log.d(TAG, "Loaded label: $line")
                    labels.add(line)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error reading labels", e)
        }
        return labels
    }

    private fun showToast(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }

    private fun showResult(result: FloatArray) {
        Log.d(TAG, "Result array: ${result.joinToString()}")

        val maxIndex = result.indices.maxByOrNull { result[it] } ?: -1

        val message = if (maxIndex in labels.indices) {
            "Keterangan: ${labels[maxIndex]}"
        } else {
            "Keterangan: Tidak dapat menentukan kondisi"
        }

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
