package ru.practicum.android.diploma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding: ActivityRootBinding get() = _binding!!

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.rootFragments) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.addOnDestinationChangedListener { _, destination, _ -> // скрываем/показываем нижнюю панель
            when (destination.id) {
                R.id.search_navigation, R.id.team_navigation, R.id.favorites_navigation, R.id.root_navigation,
                ru.practicum.android.diploma.search.R.id.searchFragment,// Временные заглушки до оформления
                ru.practicum.android.diploma.favorites.R.id.favoritesFragment,// NavGraph'а
                ru.practicum.android.diploma.team.R.id.teamFragment,// По установке навигации - удалить
                -> {
                    binding.bottomNavigationMenu.isVisible = true
                    binding.navigationPanelDivider.isVisible = true
                }
                else -> {
                    binding.bottomNavigationMenu.isVisible = false
                    binding.navigationPanelDivider.isVisible = false
                }
            }
        }

        binding.bottomNavigationMenu.setupWithNavController(navController)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
