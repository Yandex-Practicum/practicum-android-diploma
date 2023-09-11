package ru.practicum.android.diploma.root

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            startActivity(Intent(this@SplashActivity, RootActivity::class.java))
            finish()
        }
    }
    companion object{
        private const val SPLASH_SCREEN_DURATION = 1200L
    }
}