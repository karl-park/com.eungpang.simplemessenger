package com.eungpang.simplemessenger

import android.app.Application
import com.eungpang.simplemessenger.shared.ConstantPref
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.*

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (hasLoggedInBefore()) {
            // do nothing
        } else {
            // register user
            registerUser()
        }
    }

    private fun hasLoggedInBefore(): Boolean {
        val pref = getSharedPreferences(ConstantPref.KEY_PREF_NAME, MODE_PRIVATE)
        val userId = pref.getString(ConstantPref.KEY_USER_ID, null)
        return userId != null
    }

    private fun registerUser() : String {
        val userId = UUID.randomUUID().toString()
        val pref = getSharedPreferences(ConstantPref.KEY_PREF_NAME, MODE_PRIVATE)
        pref.edit()
            .putString(ConstantPref.KEY_USER_ID, userId)
            .apply()

        return userId
    }
}
