package com.tgrodz.mappolygon.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tgrodz.mappolygon.R

class SplashActivity : AppCompatActivity() {

    private lateinit var delayHandler: Handler
    private var SPLASH_DELAY: Long = 2000

    internal val runnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        delayHandler = Handler()
        delayHandler.postDelayed(runnable, SPLASH_DELAY)
    }

}
