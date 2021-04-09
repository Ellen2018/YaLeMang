package com.ellen.yalemang

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class SplashActivity:BaseSplashActivity() {

    private lateinit var button:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        button = findViewById(R.id.bt_jump)
        button.setOnClickListener {
            onUserJump()
        }
        startCountdown(10)
    }

    override fun jump() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun handlerTime(seconds: Int, remainingTime: Int) {
        button.text = "跳过:$remainingTime"
    }


}