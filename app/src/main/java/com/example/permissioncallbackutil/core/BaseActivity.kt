package com.example.permissioncallbackutil.core


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    val permissionResultHelper : ActivityPermissionResultHelper by lazy {
        ActivityPermissionResultHelper(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionResultHelper.notifyActPermissionResultObserver(requestCode, permissions, grantResults)
    }
}