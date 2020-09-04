package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraFrontViewModelFactory(private val application: Application, private val classifier: Classifier): ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFrontViewModel::class.java)) {
            return CameraFrontViewModel(application, classifier) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}