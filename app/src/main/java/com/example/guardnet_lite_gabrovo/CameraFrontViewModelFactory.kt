package com.example.guardnet_lite_gabrovo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.posenet.Posenet.Posenet

class CameraFrontViewModelFactory(private val posenet: Posenet): ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFrontViewModel::class.java)) {
            return CameraFrontViewModel(posenet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}