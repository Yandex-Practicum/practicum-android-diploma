package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.token.AccessTokenProvider
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private val tokenProvider: AccessTokenProvider by inject()
    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        authRequest(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun authRequest(accessToken: String) {
        tokenProvider.saveAccessToken(accessToken)
        val restoredToken = tokenProvider.getAccessToken()
        Log.d("RootActivity", "Token restored from prefs: $restoredToken")
    }
}
