package com.zasko.boxtool.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {


    val STORAGE_PERMS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,  // 存储空间
        Manifest.permission.READ_EXTERNAL_STORAGE
    )


    fun hasPermission(context: Context, perms: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        perms.forEach {
            val hasPer = (ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED)
            if (!hasPer) {
                return false
            }
        }
        return true
    }


    fun requestPermissions(act: Activity, perms: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(act, perms, requestCode)
    }


}