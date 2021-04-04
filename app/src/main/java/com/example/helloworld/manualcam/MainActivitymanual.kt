/*
package com.example.helloworld.manualcam

import android.os.Bundle
import android.view.View
import com.dynamsoft.camerasdk.exception.DcsCameraNotAuthorizedException
import com.dynamsoft.camerasdk.exception.DcsException
import com.dynamsoft.camerasdk.exception.DcsValueNotValidException
import com.dynamsoft.camerasdk.exception.DcsValueOutOfRangeException
import com.dynamsoft.camerasdk.model.DcsDocument
import com.dynamsoft.camerasdk.model.DcsImage
import com.dynamsoft.camerasdk.view.DcsVideoView
import com.dynamsoft.camerasdk.view.DcsVideoViewListener
import com.dynamsoft.camerasdk.view.DcsView
import com.dynamsoft.camerasdk.view.DcsViewListener
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.helloworld.R

class MainActivitymanual : AppCompatActivity(), DcsViewListener {
    private lateinit var dcsView: DcsView
    private lateinit var btShow: Button
    private val CAMERA_OK : Int = 10
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmanual)

        try {
            DcsView.setLicense(applicationContext, "your license number")
        } catch (e: DcsValueNotValidException) {
            e.printStackTrace()
        }

        btShow = findViewById(R.id.bt_show_id)
        dcsView = findViewById(R.id.dcsview_id)
        dcsView.currentView = DcsView.DVE_IMAGEGALLERYVIEW
        dcsView.setListener(this)

        try {
            dcsView.videoView.mode = DcsView.DME_DOCUMENT
        } catch (e: DcsValueOutOfRangeException) {
            e.printStackTrace()
        }

        dcsView.videoView.nextViewAfterCancel = DcsView.DVE_IMAGEGALLERYVIEW
        dcsView.videoView.nextViewAfterCapture = DcsView.DVE_EDITORVIEW

        btShow.setOnClickListener({ _ ->
            dcsView.currentView = DcsView.DVE_VIDEOVIEW
        })

        dcsView.videoView.setListener(object : DcsVideoViewListener {
            override fun onPreCapture(dcsVideoView: DcsVideoView): Boolean {
                return true
            }

            override fun onCaptureFailure(dcsVideoView: DcsVideoView, e: DcsException) {

            }

            override fun onPostCapture(dcsVideoView: DcsVideoView, dcsImage: DcsImage) {
                var dada:String=dcsImage.toString();
                Log.e("eeeeeeeeeeee",""+dada);

            }

            override fun onCancelTapped(dcsVideoView: DcsVideoView) {

            }

            override fun onCaptureTapped(dcsVideoView: DcsVideoView) {

            }

            override fun onDocumentDetected(dcsVideoView: DcsVideoView, dcsDocument: DcsDocument) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (dcsView.currentView === DcsView.DVE_VIDEOVIEW) {
            try {
                dcsView.videoView.preview()
            } catch (e: DcsCameraNotAuthorizedException) {
                e.printStackTrace()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        requestPermissions()
        if (dcsView.currentView === DcsView.DVE_VIDEOVIEW) {
            try {
                dcsView.videoView.preview()
            } catch (e: DcsCameraNotAuthorizedException) {
                e.printStackTrace()
            }

        }
    }

    override fun onStop() {
        super.onStop()
        dcsView.videoView.stopPreview()
    }

    override fun onDestroy() {
        super.onDestroy()
        dcsView.videoView.destroyCamera()
    }

    override fun onCurrentViewChanged(dcsView: DcsView, lastView: Int, currentView: Int) {

        if (currentView == DcsView.DVE_IMAGEGALLERYVIEW) {
            btShow.visibility = View.VISIBLE
        } else {
            btShow.visibility = View.GONE
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT > 22) {
            try {
                if (ContextCompat.checkSelfPermission(this@MainActivitymanual, "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivitymanual, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
                }
                if (ContextCompat.checkSelfPermission(this@MainActivitymanual, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivitymanual, arrayOf(android.Manifest.permission.CAMERA), CAMERA_OK)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            // do nothing
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            DcsView.setLicense(applicationContext, "your license number")
        } catch (e: DcsValueNotValidException) {
            e.printStackTrace()
        }

    }
}
*/
