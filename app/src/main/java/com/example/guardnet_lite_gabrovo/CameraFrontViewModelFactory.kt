package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import Mail.GMailSender
import Notifications.Notifications
import OddBehavior.OddBehavior
import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.tensorflow.lite.examples.posenet.lib.Posenet
import kotlin.reflect.KFunction0

class CameraFrontViewModelFactory(
        private val application: Application,
        private val classifier: Classifier,
        private val oddbehavior: OddBehavior,
        private val notification: Notifications,
        private val sender: GMailSender,
        private val posenet : Posenet,
        private val getBitmap: KFunction0<Bitmap?>
) : ViewModelProvider.NewInstanceFactory() {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraFrontViewModel::class.java)) {
            return CameraFrontViewModel(application, classifier, oddbehavior, notification, sender,posenet,getBitmap) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}