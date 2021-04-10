package com.ellen.yalemang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * 使用：
 * 调用startCountdown方法进行倒计时任务
 */
abstract class BaseSplashActivity : AppCompatActivity() {
    private var isUserJump = false
    private var seconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        countdownHandler = CountdownHandler(this)
    }

    /**
     * 用户点击
     */
    protected fun onUserJump() {
        isUserJump = true
        jump()
    }

    /**
     * 开始倒计时任务,应用于有计时场景
     */
    protected fun startCountdown(seconds: Int) {
        if (seconds <= 0) {
            throw Exception("倒计时不能为负数或者0:$seconds")
        }
        this.seconds = seconds
        //这里改用线程池进行改造，或者是协程
        Thread {
            run {
                for (i in 0..seconds) {
                    if (isUserJump) break
                    val message = Message()
                    message.what = i
                    countdownHandler.sendMessage(message)
                    Thread.sleep(1000)
                }
                if (!isUserJump) {
                    jump()
                }
            }
        }.start()
    }

    companion object {

        private lateinit var countdownHandler: CountdownHandler

        class CountdownHandler(baseSplashActivity: BaseSplashActivity) : Handler() {
            //采用弱引用的方式防止内存泄漏
            private var weakReference: WeakReference<BaseSplashActivity> =
                WeakReference(baseSplashActivity)

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val baseSplashActivity = weakReference.get()
                baseSplashActivity?.handlerTime(msg.what, baseSplashActivity.seconds - msg.what)
            }
        }
    }

    /**
     * 跳转
     */
    abstract fun jump()

    /**
     * 每过一秒都会调用此方法
     */
    abstract fun handlerTime(seconds: Int, remainingTime: Int)
}