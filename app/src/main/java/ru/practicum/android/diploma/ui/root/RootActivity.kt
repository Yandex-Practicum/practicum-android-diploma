package ru.practicum.android.diploma.ui.root

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)

        // Получаем NavController из NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Связываем BottomNavigationView с NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Слушаем переключение экранов
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateBottomNav(destination)
        }

        // Применяем сразу для стартового экрана
        binding.root.post {
            navController.currentDestination?.let { updateBottomNav(it) }
        }
    }

    // Управление видимостью нижнего меню
    private fun updateBottomNav(destination: NavDestination) {
        val isBottomNavVisible = when (destination.id) {
            R.id.searchFragment,
            R.id.favoritesFragment,
            R.id.teamFragment -> true
            else -> false
        }

        // Видимость нижнего меню и разделителя
        binding.bottomNavigationView.isVisible = isBottomNavVisible
        binding.dividerLine.isVisible = isBottomNavVisible
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
