package ru.practicum.android.diploma.ui.root

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
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

        // Устанавливаем toolbar как ActionBar
        setSupportActionBar(binding.toolbar)

        // Связываем BottomNavigationView с NavController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Управление видимостью нижнего меню и toolbar (+ начальная title)
        fun updateUi(destinationId: Int?) {
            // Заголовок из nav_graph.xml
            navController.addOnDestinationChangedListener { _, destination, _ ->
                // Заголовок из nav_graph.xml
                val currentDestination = navController.currentDestination
                binding.toolbar.title = currentDestination?.label ?: getString(R.string.app_name)

                val isBottomNavVisible = when (destination.id) {
                    R.id.searchFragment,
                    R.id.favoritesFragment,
                    R.id.teamFragment -> true
                    else -> false
                }

                // Управление иконкой "назад"
                if (isBottomNavVisible) {
                    // Только текст
                    binding.toolbar.navigationIcon = null
                } else {
                    // + стрелка назад
                    binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
                    binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
                }

                // Видимость нижнего меню и разделителя
                binding.bottomNavigationView.isVisible = isBottomNavVisible
                binding.dividerLine.isVisible = isBottomNavVisible
            }
        }

        // Слушаем переключение экранов
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateUi(destination.id)
        }

        // Применяем сразу для стартового экрана (после полной готовности)
        binding.root.post { updateUi(navController.currentDestination?.id) }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
