package com.mahvin.stresslezz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mahvin.stresslezz.MainActivity
import com.mahvin.stresslezz.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isOnboardingCompleted = sharedPref.getBoolean("onboarding_completed", false)

        val intent = if (isOnboardingCompleted) {
            Intent(this, MainActivity::class.java)
        } else {

            Intent(this, OnboardingActivity::class.java)
        }

        startActivity(intent)
        finish()
    }
}