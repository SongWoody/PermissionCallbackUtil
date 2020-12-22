package com.example.permissioncallbackutil.core

import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

/**
 * Activity 의 onRequestPermissionsResult 를 간단하게 사용하기 위해 생성
 */
typealias PermissionCallback = (isAllGranted: Boolean, permissions: Array<out String>, grantResults: IntArray) -> Unit

class ActivityPermissionResultHelper(private val mContext: AppCompatActivity) {
    private val mActPermissionResultCallbackList =
        SparseArray<PermissionCallback> ()

    /**
     * requestCode 에 대응되는 observer 찾아서 결과 값 전달 후 ActPermissionResultObserver 삭제
     */
    fun notifyActPermissionResultObserver(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mActPermissionResultCallbackList[requestCode]?.let {
            it(isAllGranted(grantResults), permissions, grantResults)
            mActPermissionResultCallbackList.delete(requestCode)
        }
    }

    /**
     * observer 추가 및 startActivityForResult 실행
     */
    fun addActPermissionResultObserver(
        requestCode: Int,
        permissionList: Array<String>,
        atcResultCallback: PermissionCallback
    ) {
        mActPermissionResultCallbackList.append(requestCode, atcResultCallback)
        ActivityCompat.requestPermissions(mContext, permissionList, requestCode)
    }

    /**
     * 모두 동의했는지 체크
     */
    private fun isAllGranted(grantResults: IntArray): Boolean {
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {
               return false
            }
        }
        return true
    }

}