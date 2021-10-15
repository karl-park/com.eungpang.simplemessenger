package com.eungpang.simplemessenger.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.shared.ConstantPref
import com.eungpang.simplemessenger.ui.friends.FriendsActivity
import com.eungpang.simplemessenger.ui.login.LoginActivity

// We can migrate it to Android 12 Splash Screen
class SplashActivity : AppCompatActivity() {
    companion object {
        private const val INTERVAL = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (hasLoggedInBefore()) {
                startActivity(Intent(this, FriendsActivity::class.java))
            } else {
                // register user
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()
        }, INTERVAL)
    }

    private fun hasLoggedInBefore(): Boolean {
        val pref = getSharedPreferences(ConstantPref.KEY_PREF_NAME, MODE_PRIVATE)
        val userId = pref.getString(ConstantPref.KEY_USER_ID, null)
        return userId != null
    }
}