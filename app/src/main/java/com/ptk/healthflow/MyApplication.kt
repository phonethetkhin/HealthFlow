package com.ptk.healthflow

import android.app.Application
import com.ptk.healthflow.util.NotificationUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthFlowApp : Application(){
    override fun onCreate() {
        super.onCreate()
        NotificationUtils.createNotificationChannel(this)

    }

}
