package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import Mail.GMailSender
import Notifications.Notifications
import OddBehavior.OddBehavior
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import androidx.core.content.res.ResourcesCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CameraFrontViewModel(private val classifier: Classifier, private val context : Context) : ViewModel() {

    private lateinit var oddbehavior: OddBehavior
    private var notification : Notifications? = null
    private var sender : GMailSender? = null
    @RequiresApi(Build.VERSION_CODES.O)
    fun runForever(bitmap: Bitmap) {

        oddbehavior = OddBehavior.getInstance()
        notification = Notifications()
        sender = GMailSender()
        notification!!.createNotification(context.resources.getString(R.string.Title), context.resources.getString(R.string.Body),context) // OK
        // start a new coroutine in the ViewModel
        viewModelScope.launch {
            // cancelled when the ViewModel is cleared
            while (true) {
                delay(100)
                // do something every 100 ms
                if(doDetection(bitmap)){
                    SendNotifications(bitmap)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun SendNotifications(bitmap: Bitmap)
    {
        var fileName : String = getCurrentDateAndTime().plus(".png")
        saveBitmap(bitmap,fileName)
        sender!!.sendMail(context.resources.getString(R.string.Title), context.resources.getString(R.string.Body), "spstrademark@outlook.com", fileName);


    }



    fun closePosenet() {
        classifier.close()
    }

    private fun doDetection(bitmap: Bitmap?) : Boolean {
        if (bitmap == null) return false
        val startTime   = SystemClock.elapsedRealtimeNanos()
        val kp          = classifier.get_positions(bitmap)
        val bodyPos     = classifier.getBodyPartsPosition(bitmap,kp)
        classifier.drawPoints(bitmap,bodyPos);

        val endTime     = SystemClock.elapsedRealtimeNanos() - startTime
        Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
        return oddbehavior.isBehaviorOdd(kp);
    }

    private fun getCurrentDateAndTime(): String {
        // will be used for saving bitmaps
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss")
        dateFormatter.isLenient = false
        val today = Date()
        return dateFormatter.format(today)
    }

    private fun saveBitmap(bm: Bitmap?, filename : String?) {
//        if (bm != null) {
//            try {
//                val path = String.format("%s%s%s",
//                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//                        File.separator,
//                        resources.getString(R.string.app_name))
//
//           //     val file = File(path, "/aaaa.png")
//               val file = File(path, ("/" + filename))
//                val fOut: OutputStream = FileOutputStream(file)
//                bm.compress(Bitmap.CompressFormat.PNG, 50, fOut)
//                fOut.flush()
//                fOut.close()
//                bm.recycle()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

}