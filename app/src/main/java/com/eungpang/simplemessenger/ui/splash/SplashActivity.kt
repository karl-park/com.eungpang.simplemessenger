package com.eungpang.simplemessenger.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.eungpang.simplemessenger.R
import com.eungpang.simplemessenger.ui.friends.FriendsActivity

// We can migrate it to Android 12 Splash Screen
class SplashActivity : AppCompatActivity() {
    companion object {
        private const val INTERVAL = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, FriendsActivity::class.java))
            finish()
        }, INTERVAL)
    }
}