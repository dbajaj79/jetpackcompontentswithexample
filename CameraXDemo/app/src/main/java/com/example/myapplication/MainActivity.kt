package com.example.myapplication

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.common.showToast
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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

    }

    fun startCamera() {

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
