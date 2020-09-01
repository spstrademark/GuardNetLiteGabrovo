package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraFrontViewModel(private val classifier: Classifier) : ViewModel() {


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

    fun closePosenet() {
        classifier.close()
    }

    private fun doDetection(bitmap: Bitmap?) {
        if (bitmap == null) return
        val startTime = SystemClock.elapsedRealtimeNanos()
        val kp =    classifier.get_positions(bitmap)
        val bodyPos = classifier.getBodyPartsPosition(bitmap,kp)
        val endTime = SystemClock.elapsedRealtimeNanos() - startTime
        Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
    }

}