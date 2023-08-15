package com.zasko.boxtool

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.core.content.getSystemService
import com.zasko.boxtool.components.HttpComponent
import com.zasko.boxtool.helper.LogUtil

class MyApplication : Application() {

    companion object {

        const val TAG = "MyApplication"
        lateinit var application: Application


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }


    override fun onCreate() {
        super.onCreate()

        if (isMainPrecess()) {
            componentsInit()
        }
    }

    private fun isMainPrecess(): Boolean {
        val name = getCurrentProcessName()
        LogUtil.dPrintln("$TAG isMainPrecess name:${name}")
        return packageName == name
    }


    private fun getCurrentProcessName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getProcessName()
        } else {
            val am = getSystemService<ActivityManager>()
            val runningProcess = am?.runningAppProcesses ?: emptyList()
            val currentPid = Process.myPid()
            val currentProcess = runningProcess.find { it.pid == currentPid }
            currentProcess?.processName ?: ""
        }
    }

    private fun componentsInit() {
        HttpComponent.init()
    }
}