package com.mandarin.bcu.androidutil

import android.util.Log

/**
 * Notes for crash reports. The official app sends these to the BCU team's Firebase project; this build has no
 * Firebase, so they go to Android's log (adb logcat, tag "BCU").
 */
object CrashLog {
    @JvmStatic
    fun log(message: String) {
        Log.i("BCU", message)
    }
}
