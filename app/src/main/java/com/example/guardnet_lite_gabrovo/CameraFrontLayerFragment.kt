package com.example.guardnet_lite_gabrovo

import Ai.Classifier
import Ai.TFLiteDetector
import Common.SettingsUtils
import Device.DeviceHandler
import Device.UserDevice
import Mail.GMailSender
import Notifications.Notifications
import OddBehavior.OddBehavior
import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.coroutines.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.internal.Intrinsics
import kotlin.math.abs


class CameraFrontLayerFragment : Fragment() {

    private lateinit var settings: SettingsUtils
    private var webView: WebView? = null
    private var dropdown: MaterialSpinner? = null
    private var addCameraButton: FloatingActionButton? = null
    private var hideBackdropButton: ImageView? = null
    private lateinit var viewPager: ViewPager2
    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    private var playWhenReady = false
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    private lateinit var userDevicesList: List<UserDevice>
    private lateinit var cameraFrontViewModel: CameraFrontViewModel
    private val camerasURLList: MutableList<String> = ArrayList()

    private lateinit var sender: GMailSender
    private lateinit var notification: Notifications

    private var selected = 0
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val TF_LITE_SIZE = 337
    private val eventListener = object : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playWhenReady && playbackState == Player.STATE_READY) { // media playing
                val textureView = playerView.videoSurfaceView as TextureView
                var bitmap = textureView.bitmap

                Log.d("CameraFront", "bitmap: $bitmap")
                if (bitmap != null) {
                    // wait for the right bitmap
                    //        val choppedBitmap = cropBitmap(bitmap)
                    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, TF_LITE_SIZE, TF_LITE_SIZE, true)
                    doInfiniteTask(resizedBitmap) // start infinite task of getting bitmaps
                }
            } else if (playWhenReady) {
                // might be idle (plays after prepare()),
                // buffering (plays when data available)
                // or ended (plays when seek away from end)
            } else {
                // player paused in any state
                Log.d("CameraFront", "player is paused")
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_camera_front_layer, container, false)
        setHasOptionsMenu(true)
        requestPermission()
        initVars(view)

        sender = GMailSender(context)
        notification = Notifications()

        // init the ViewModel
        val viewModelFactory = CameraFrontViewModelFactory(
                requireActivity().application,
                TFLiteDetector.create(requireContext(),
                        null,
                        R.raw.posenet_trademark_v1,
                        TF_LITE_SIZE,
                        TF_LITE_SIZE),
                OddBehavior.getInstance(),
                notification,
                sender
        )

        cameraFrontViewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(CameraFrontViewModel::class.java)

        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_bo_alarm_24px)
                1 -> tab.setIcon(R.drawable.ic_bo_event_24px)
                2 -> tab.setIcon(R.drawable.ic_bo_gallery_24px)
            }
        }.attach()

        val tabFirst = tabLayout.getTabAt(0)
        tabFirst?.icon?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                tab?.icon?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(requireContext(), R.color.colorText)
                tab?.icon?.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        // Set up the tool bar
        setUpBackdropButton(view)
        setupAddDeviceButton()
//        initializePlayer("https://192.168.0.101:8080")
        initCamerasURL()
        setupCameraSpinner()

        playerStart(selected)

        return view
    }

    private fun initVars(view: View) {
        settings = SettingsUtils.getInstance()
        webView = view.findViewById(R.id.frontLayerWebView)
        dropdown = view.findViewById(R.id.cameraListSpinner)
        addCameraButton = view.findViewById(R.id.button_add)
        hideBackdropButton = view.findViewById(R.id.hideBackdropButton)
        viewPager = view.findViewById(R.id.viewPager)
        playerView = view.findViewById(R.id.playerView)

        settings.initAppFolder(resources.getString(R.string.app_name))
        settings.getLanguage()
        userDevicesList = DeviceHandler(settings).allDevices//devhandler.allDevices
        Log.d("CameraFrontLayer", "temp, userDevicesList: $userDevicesList")
        //  val test = settings.getCamera()
        selected = settings.getCamera()
    }

    override fun onDestroy() {
        job.cancel()
        cameraFrontViewModel.closePosenet()
        super.onDestroy()
    }

    override fun onResume() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            playerStart(selected)
        }
        super.onResume()
    }

    override fun onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            playerStart(selected)
        }
        super.onStart()
    }

    override fun onPause() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            releasePlayer()
        }
        super.onPause()
    }

    override fun onStop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releasePlayer()
        }
        super.onStop()
    }

    @Synchronized
    fun getM3u8Playlist(embedUrl: String): String? {
        var result: String? = null
        result = runBlocking {
            val deferredResult = async(Dispatchers.IO) {
                try {
                    val html: Connection.Response = Jsoup.connect(embedUrl).execute()
                    val statusCode = html.statusCode()
                    if (statusCode == 200) {
                        val dok: Document = Jsoup.parse(html.body(), embedUrl)
                        val scripts: Elements = dok.getElementsByTag("script")
                        val urlScript: String = scripts.last().toString()
                        val startIdx = urlScript.indexOf("https://")
                        val endIdx = urlScript.indexOf(";")

                        if (startIdx != -1 && endIdx != -1) {
                            val tmp = urlScript.substring(startIdx, endIdx)
                            result = tmp.replace("\"", "")
                        }
                    }
                } catch (e: Exception) {

                }

            }
            deferredResult.await()
            result
        }
        Log.d("CameraFrontLayer", "temp, result: $result")
        return result
    }

    private fun initializePlayer(videoUrl: String?) {
        playWhenReady = false
        try {
            val uri = Uri.parse(videoUrl)
            val mediaSource = buildHlsMediaSource(uri) ?: return
//        val mediaSource = buildHttpMediaSource(uri) ?: return
            //       val mediaSource = buildLocalMediaSource()
            playerView.useController = false

            player = SimpleExoPlayer.Builder(requireContext()).build()
            playerView.requestFocus()
            playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
            playerView.player = player
            player.playWhenReady = playWhenReady

            player.seekTo(currentWindow, playbackPosition)
            player.prepare(mediaSource, false, false)
            player.addListener(eventListener)
            player.playWhenReady = true //run file/link when ready to play.
        } catch (e: Exception) {

        }

    }

    private fun releasePlayer() {
        try {
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            player.removeListener(eventListener)
            player.stop()
            player.release()
            playWhenReady = player.playWhenReady
        } catch (e: Exception) {

        }

    }

    private fun buildHttpMediaSource(uri: Uri): ProgressiveMediaSource? {
        //  DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeter);
//        val dataSourceFactory = DefaultHttpDataSourceFactory(
//                Util.getUserAgent(requireContext(), "exoplayer2example"),
//                null /* listener */,
//                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
//                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
//                true /* allowCrossProtocolRedirects */
//        )

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(requireContext(), "exoplayer")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

//    private fun buildLocalMediaSource() : MediaSource{
//        // Produces DataSource instances through which media data is loaded.
//
//        // Produces DataSource instances through which media data is loaded.
//        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(requireContext(), "exoplayer")
//
//        // This is the MediaSource representing the media to be played.
//
//        // This is the MediaSource representing the media to be played.
//        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(RawResourceDataSource.buildRawResourceUri(R.raw.aggr))
//
//    }

    private fun buildHlsMediaSource(uri: Uri): HlsMediaSource? {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(requireContext(), "exoplayer")
        return HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun setUpBackdropButton(view: View) {
        hideBackdropButton?.setOnClickListener(NavigationIconClickListener(
                context,
                view.findViewById(R.id.front_layer),
                AccelerateDecelerateInterpolator(),
                ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_keyboard_arrow_down_24px, null),  // Menu open icon
                ResourcesCompat.getDrawable(requireContext().resources, R.drawable.ic_expand_more_24px, null))) // Menu close icon
    }

    private fun setupCameraSpinner() {
        val userDevices: MutableList<String> = ArrayList()
        if (!userDevicesList.isNullOrEmpty()) {
            for (device in userDevicesList) {
                if (device.GetName() != null) {
                    userDevices.add(device.GetName())
                } else {
                    userDevices.add(device.GetURL())
                }
            }
            Log.d("CameraFrontLayer", "temp, userDevices: ${userDevices}")
        }
        val cameraList = listOf(*resources.getStringArray(R.array.PublicCameras))
        userDevices.addAll(cameraList)
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, userDevices)
        dropdown?.setAdapter(adapter)
        dropdown?.selectedIndex = selected
        dropdown?.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { view: MaterialSpinner?, position: Int, id: Long, item: String? ->
            settings.saveSelectedCamera(position)

            val playList: String? = getM3u8Playlist(getCameraURL(position))
            val uri = Uri.parse(playList)
            val mediaSource = buildHlsMediaSource(uri)
            if (mediaSource != null) {
                player.prepare(mediaSource, false, false)
            }

        })
    }

    private fun initCamerasURL() {
        if (!userDevicesList.isNullOrEmpty()) {
            for (device in userDevicesList) {
                camerasURLList.add(device.GetURL())
            }
        }
        val publicURLList = listOf(*resources.getStringArray(R.array.PublicCamerasEmbed))
        camerasURLList.addAll(publicURLList)
        //    selected = settings.getCamera()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initWebView() {
        val webSettings = webView?.settings
        webSettings?.javaScriptEnabled = true
        webSettings?.allowContentAccess = true
        webSettings?.setAppCacheEnabled(true)
        webSettings?.domStorageEnabled = true
        webSettings?.useWideViewPort = true
        webView?.isHorizontalScrollBarEnabled = false
        webView?.isVerticalScrollBarEnabled = false
        webView?.setOnTouchListener { v: View?, event: MotionEvent -> event.action == MotionEvent.ACTION_MOVE }
    }

    private fun viewerStart(position: Int) {
        val webContent = "<!DOCTYPE html>" +
                "<html> " +
                "<head> " +
                "<meta charset=\\\"UTF-8\\\"><meta name=\\\"viewport\\\" content=\\\"target-densitydpi=high-dpi\\\" /> " +
                "<meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1\\\"> <link rel=\\\"stylesheet\\\" " +
                "media=\\\"screen and (-webkit-device-pixel-ratio:1.5)\\\" href=\\\"hdpi.css\\\" />" +
                "</head> " +
                "<body onload=\"ClickFrame()\" style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;width:450px;height:254;\">" +
                "<iframe id=\"view\" type=type=\"text/html\" width=\"450\" height=\"254\" src=\"%s\" ></iframe>" +
                "<script type=\"text/javascript\">" +
                "function ClickFrame(){" +
                "setTimeout(function(){ document.getElementById('view').click(); }, 3000);" +  //      "document.getElementById('view').click();" +
                "}" +
                "</script>" +
                "</body" +
                "></html>"
        val playVideo = String.format(webContent, getCameraURL(position))
        Log.d("Main", "temp, playVideo: $playVideo")
        webView?.loadData(playVideo, "text/html", "utf-8")
    }

    private fun playerStart(position: Int) {
        if (isValidURL(getCameraURL(position))) {
            val playList: String? = getM3u8Playlist(getCameraURL(position))
            initializePlayer(playList)
        }
    }

    private fun getCameraURL(idx: Int): String {
        return camerasURLList[idx]
    }

    private fun setupAddDeviceButton() {
        addCameraButton?.setOnClickListener { v: View? ->
            NavHostFragment.findNavController(this@CameraFrontLayerFragment)
                    .navigate(R.id.action_CameraFragment_to_AddFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsFragment) {
            NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(this))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private val bitmap: Bitmap?
        get() {
            val bm = arrayOf<Bitmap?>(null)
            webView?.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    webView?.measure(View.MeasureSpec.makeMeasureSpec(
                            View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                    val measuredWidth = webView?.measuredWidth ?: 0
                    val measuredHeight = webView?.measuredHeight ?: 0
                    if (measuredWidth > 0 && measuredHeight > 0) {
                        return
                    }
                    webView?.layout(0, 0, measuredWidth,
                            measuredHeight)
                    webView?.isDrawingCacheEnabled = true
                    webView?.buildDrawingCache()
                    bm[0] = Bitmap.createBitmap(measuredWidth,
                            measuredHeight, Bitmap.Config.ARGB_8888)
                }
            }
            return bm[0]
        }

    private fun doInfiniteTask(bitmap: Bitmap) {
        cameraFrontViewModel.runForever(bitmap)
    }

    private fun isValidURL(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
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
