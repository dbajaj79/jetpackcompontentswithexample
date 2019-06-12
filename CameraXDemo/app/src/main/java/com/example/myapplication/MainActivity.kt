package com.example.myapplication

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.common.showToast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),LifecycleOwner {

    lateinit var activityMainBinding: ActivityMainBinding
    val REQUEST_CAMERA_PERMISSION = 100
    val REQUEST_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        if(allPermissionsGranted())
        {
            activityMainBinding.preview.post{startCamera()}
        }
        else
        {
            askCameraPermission()
        }

    }



    private fun updateTranformation()
    {
            val matrix = Matrix()
            val centerY = activityMainBinding.preview.height/2f
            val centerX = activityMainBinding.preview.width/2f

        val rotationDegree = when(activityMainBinding.preview.display.rotation)
        {
            Surface.ROTATION_0-> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else->
                return
        }
        matrix.postRotate(-rotationDegree.toFloat(),centerX,centerY)
        activityMainBinding.preview.setTransform(matrix)

    }


    private fun startCamera() {

        // Create configuration object for the viewfinder use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(640, 640))
        }.build()

        // Build the viewfinder use case
        val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = activityMainBinding.preview.parent as ViewGroup
            parent.removeView(activityMainBinding.preview)
            parent.addView(activityMainBinding.preview, 0)

            activityMainBinding.preview.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview)
    }


    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = activityMainBinding.preview.width / 2f
        val centerY = activityMainBinding.preview.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(activityMainBinding.preview.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        activityMainBinding.preview.setTransform(matrix)
    }
    /*
    fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640,640))
            setTargetAspectRatio(Rational(1,1))
        }.build()
        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = activityMainBinding.preview.parent as ViewGroup
            parent.removeView(activityMainBinding.preview)
            parent.addView(activityMainBinding.preview,0)
            activityMainBinding.preview.surfaceTexture = it.surfaceTexture
            updateTranformation()
        }
        CameraX.bindToLifecycle(this,preview)
    }
*/
    override fun getLifecycle(): Lifecycle {
        return this.lifecycle
    }
    fun askCameraPermission()
    {
        if(ContextCompat.checkSelfPermission(this,REQUEST_PERMISSIONS[0])!=PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,REQUEST_PERMISSIONS[1])!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,REQUEST_PERMISSIONS,REQUEST_CAMERA_PERMISSION)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==REQUEST_CAMERA_PERMISSION)
        {
            if(allPermissionsGranted())
            {
                activityMainBinding.preview.post { startCamera() }
            }
            else
            {
                showToast("Permission not Granted")
                finish()
            }
        }

    }

    private fun allPermissionsGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this, it) == PackageManager.PERMISSION_GRANTED
    }

}
