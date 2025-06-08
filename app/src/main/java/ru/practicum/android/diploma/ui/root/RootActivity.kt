package ru.practicum.android.diploma.ui.root

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    @Suppress("LateinitUsage")
    private lateinit var binding: ActivityRootBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupThemeAndStatusBar()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.favoriteFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.teamFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.filterFragment -> binding.bottomNavigationView.visibility = View.GONE
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == navController.graph.startDestinationId) {
                    showExitConfirmationDialog()
                } else {
                    navController.navigateUp()
                }
            }
        })

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainFragment -> {
                    navController.navigate(R.id.mainFragment)
                    true
                }
                R.id.favoriteFragment -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
                R.id.teamFragment -> {
                    navController.navigate(R.id.teamFragment)
                    true
                }
                else -> false
            }
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.exit_confirmation)
            .setMessage(R.string.are_you_sure_you_want_to_exit)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setupThemeAndStatusBar() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    private fun syncStatusBarWithAppTheme() {
        val isDarkTheme =
            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_YES

        when {
            // For Android 11+ (API 30+)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.insetsController?.let {
                    val appearance = if (isDarkTheme) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS

                    it.setSystemBarsAppearance(appearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
                }
            }
            // For Android 6+ (API 23+)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                window.decorView.systemUiVisibility = if (isDarkTheme) {
                    window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                } else {
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        syncStatusBarWithAppTheme()
    }

    override fun onResume() {
        super.onResume()
        syncStatusBarWithAppTheme()
        binding.bottomNavigationView.isVisible = true
    }

}
