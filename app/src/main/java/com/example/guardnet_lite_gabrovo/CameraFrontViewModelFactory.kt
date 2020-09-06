package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import Mail.GMailSender
import Notifications.Notifications
import OddBehavior.OddBehavior
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraFrontViewModelFactory(
        private val application: Application,
        private val classifier: Classifier,
        private val oddbehavior: OddBehavior,
        private val notification: Notifications,
        private val sender: GMailSender
) : ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFrontViewModel::class.java)) {
            return CameraFrontViewModel(application, classifier, oddbehavior, notification, sender) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}