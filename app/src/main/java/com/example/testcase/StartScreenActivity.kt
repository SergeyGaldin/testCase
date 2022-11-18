package com.example.testcase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StartScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SplashScreen)
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}