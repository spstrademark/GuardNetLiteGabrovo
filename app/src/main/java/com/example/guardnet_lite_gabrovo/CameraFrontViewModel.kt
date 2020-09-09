package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import Common.SettingsUtils
import Mail.GMailSender
import Notifications.Notifications
import OddBehavior.OddBehavior
import android.app.Application
import android.graphics.Bitmap
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tensorflow.lite.examples.posenet.lib.Posenet
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KFunction0

class CameraFrontViewModel(
        application: Application,
        private val classifier: Classifier,
        private val oddbehavior: OddBehavior,
        private val notification: Notifications,
        private val sender: GMailSender,
        private val posenet: Posenet,
        private val getBitmap: KFunction0<Bitmap?>
) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val settings: SettingsUtils = SettingsUtils.getInstance()
//    private val notificationsUtils : NotificationsUtils = NotificationsUtils.getInstance()

    private var wait = false
    private var timer : Long = 0

    fun runForever(unused: Bitmap?) {
        // start a new coroutine in the ViewModel
        viewModelScope.launch {
            // cancelled when the ViewModel is cleared
            while (true) {
              val bitmap =  getBitmap();

                delay(100)
                if(bitmap!=null){
                    val bmp: Bitmap = bitmap.copy(bitmap.getConfig(), true)
                    if (doDetection(bitmap)) {
                        sendNotifications(bmp)
                    }

                }
            }
        }
    }


    private fun sendNotifications(bitmap: Bitmap) {
        var fileName: String = getCurrentDateAndTime().plus(".png")
        saveBitmap(bitmap, fileName)
        val Mails: String = settings.notificationsGetMails()

        if(Mails!=null){
            if (settings.notificationsSendGet()) {
                Thread {
                    // do the async Stuff

                    sender.sendMail(context.resources.getString(R.string.Title), context.resources.getString(R.string.Body), "spstrademark@outlook.com", fileName);
                    // maybe do some more stuff
                }.start()
            }
        }


        if (settings.notificationsNotifyGet())
            notification.createNotification(context.resources.getString(R.string.Title), context.resources.getString(R.string.Body), context) // OK

    }

    fun closePosenet() {
        classifier.close()
    }

    private fun doDetection(bitmap: Bitmap?): Boolean {
        if (bitmap == null) return false
        var result = false
        val startTime = SystemClock.elapsedRealtimeNanos()
        val kp = posenet.GeyKeyPoints(bitmap)
     //   val kp = classifier.get_positions(bitmap)
        if(kp==null) return false;
    //    val bodyPos = classifier.getBodyPartsPosition(bitmap, kp)
//    classifier.drawPoints(bitmap,bodyPos);
        val endTime = SystemClock.elapsedRealtimeNanos() - startTime
        Log.i("posenet", String.format("Thread took %.2f ms", 1.0f * endTime / 1000000))
      //  if (bodyPos != null)

            Log.i("posenet", String.format("SIZE: %d", kp.keyPoints.size))

        if(timer >= settings.notificationSecondsTriggerGet() * 1000){
            timer = 0
        }

        if(timer.compareTo(0)==0){
            result = oddbehavior.isBehaviorOdd(kp, settings.notificationSecondsTriggerGet() * 1000)
        }

        if(result || timer.compareTo(0)!=0){
            timer += endTime
        }



        return result
    }

    private fun getCurrentDateAndTime(): String {
        // will be used for saving bitmaps
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss")
        dateFormatter.isLenient = false
        val today = Date()
        return dateFormatter.format(today)
    }

    private fun saveBitmap(bm: Bitmap?, filename: String?) {
        if (bm != null) {
            try {
                val path = String.format("%s%s%s",
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                        File.separator,
                        context.resources.getString(R.string.app_name))

                //     val file = File(path, "/aaaa.png")
                val file = File(path, ("/" + filename))
                val fOut: OutputStream = FileOutputStream(file)
                bm.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                bm.recycle()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}