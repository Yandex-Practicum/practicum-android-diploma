package ru.practicum.android.diploma.root

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import ru.practicum.android.diploma.R

class SplashActivity : AppCompatActivity() {
    private var mSplash_Timer = 1200 // Splash screen timer in milliseconds
    private val mTimeCounter = object : CountDownTimer(mSplash_Timer.toLong(), 100) {
        override fun onTick(p0: Long) {
            // Not used in this example
        }

        override fun onFinish() {
            mNextPage(Intent(this@SplashActivity, RootActivity::class.java))
        }
    }

    private fun mNextPage(intent: Intent) {
        startActivity(intent)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        mTimeCounter.start()
    }
}