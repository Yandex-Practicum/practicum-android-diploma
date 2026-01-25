package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)

        // Получаем NavController из NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Связываем BottomNavigationView с NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Управление видимостью нижнего меню
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment,
                R.id.favoritesFragment,
                R.id.teamFragment -> {
                    // Показываем на основных экранах
                    binding.bottomNavigationView.isVisible = true
                }
                R.id.filterFragment,
                R.id.vacancyDetailsFragment -> {
                    // Скрываем на вспомогательных экранах
                    binding.bottomNavigationView.isVisible = false
                }
                else -> {
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
