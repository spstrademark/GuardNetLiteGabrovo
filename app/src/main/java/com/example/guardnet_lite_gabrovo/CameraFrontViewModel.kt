package com.example.guardnet_lite_gabrovo

import android.graphics.Bitmap
import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.posenet.Posenet.Posenet
import kotlin.jvm.internal.Intrinsics
import kotlin.math.abs

class CameraFrontViewModel(private val posenet: Posenet) : ViewModel() {


    fun runForever(bitmap: Bitmap) {
        // start a new coroutine in the ViewModel
        viewModelScope.launch {
            // cancelled when the ViewModel is cleared
            while (true) {
                delay(100)
                // do something every 100 ms
                doDetection(bitmap)
            }
        }
    }

    private fun doDetection(bitmap: Bitmap?) {
        if (bitmap == null) return
        val startTime = SystemClock.elapsedRealtimeNanos()

        val copy = bitmap.copy(bitmap.config, false)
        val choppedBitmap = cropBitmap(copy)
        val resized = Bitmap.createScaledBitmap(choppedBitmap, 257, 257, true)
        posenet.GeyKeyPoints(resized)
        val endTime = SystemClock.elapsedRealtimeNanos() - startTime
//            Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
    }

    private fun cropBitmap(bitmap: Bitmap): Bitmap {
        val bitmapRatio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val modelInputRatio = 1.0f
        val maxDifference = 1.0E-5
        var cropHeight = modelInputRatio - bitmapRatio
        val var8 = false
        return if (abs(cropHeight) < maxDifference) {
            bitmap
        } else {
            val var10000: Bitmap
            val croppedBitmap: Bitmap
            if (modelInputRatio < bitmapRatio) {
                cropHeight = bitmap.height.toFloat() - bitmap.width.toFloat() / modelInputRatio
                var10000 = Bitmap.createBitmap(bitmap, 0, (cropHeight / 2.toFloat()).toInt(), bitmap.width, (bitmap.height.toFloat() - cropHeight).toInt())
                Intrinsics.checkExpressionValueIsNotNull(var10000, "Bitmap.createBitmap(\n   …Height).toInt()\n        )")
                croppedBitmap = var10000
            } else {
                cropHeight = bitmap.width.toFloat() - bitmap.height.toFloat() * modelInputRatio
                var10000 = Bitmap.createBitmap(bitmap, (cropHeight / 2.toFloat()).toInt(), 0, (bitmap.width.toFloat() - cropHeight).toInt(), bitmap.height)
                Intrinsics.checkExpressionValueIsNotNull(var10000, "Bitmap.createBitmap(\n   …  bitmap.height\n        )")
                croppedBitmap = var10000
            }
            croppedBitmap
        }
    }
}