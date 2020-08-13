package com.example.permissioncallbackutil

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.permissioncallbackutil.core.BaseActivity
import com.example.permissioncallbackutil.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.requireButton.setOnClickListener() {
            GlobalScope.launch {
                val isGrantedAll = withContext(Dispatchers.Main) { requestPermission() }
                runOnUiThread {
                    Toast.makeText(this@MainActivity,"is Granted All: [$isGrantedAll]", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun requestPermission() : Boolean = suspendCoroutine { const ->
        // List of permissions to request
        val permissionList: Array<String?> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionResultHelper.addActPermissionResultObserver(200, permissionList) { isAllGranted, permissions, grantResults ->
                const.resume(isAllGranted)
            }
        } else {
            const.resume(true)
        }
    }
}
