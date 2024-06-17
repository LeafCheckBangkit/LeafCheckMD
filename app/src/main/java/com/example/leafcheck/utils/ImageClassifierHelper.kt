package com.example.leafcheck.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import com.example.leafcheck.ml.ModelFix95
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import java.nio.ByteBuffer
import java.io.BufferedReader
import java.io.InputStreamReader

class ImageClassifierHelper(
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            result: FloatArray?,
            inferenceTime: Long
        )
    }

    fun classifyStaticImage(imageUri: Uri) {
        val model: ModelFix95
        try {
            model = ModelFix95.newInstance(context)
        } catch (e: Exception) {
            classifierListener?.onError("Error initializing model: ${e.message}")
            return
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(NormalizeOp(0.0f, 1.0f))  // Normalizing to range [0, 1]
            .build()


        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(toBitmap(imageUri))
        val processedImage = imageProcessor.process(tensorImage)

        val byteBuffer: ByteBuffer = processedImage.buffer

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        var inferenceTime = SystemClock.uptimeMillis()
        try {
            val outputs = model.process(inputFeature0)
            inferenceTime = SystemClock.uptimeMillis() - inferenceTime
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            if (outputFeature0.size != 6) {
                classifierListener?.onError("Model output size mismatch: ${outputFeature0.size} != 6")
                return
            }

            classifierListener?.onResults(outputFeature0, inferenceTime)
            Log.d(TAG, "Output probabilities: ${outputFeature0.joinToString()}")
        } catch (e: Exception) {
            classifierListener?.onError("Error during model inference: ${e.message}")
        } finally {
            model.close()
        }
    }

    private fun toBitmap(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }.copy(Bitmap.Config.ARGB_8888, true)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
