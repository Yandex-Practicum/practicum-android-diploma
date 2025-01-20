package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filterCommonFragment,
                R.id.filterCountryRegionFragment,
                R.id.filterCountryFragment,
                R.id.filterRegionFragment,
                R.id.filterIndustryFragment,
                R.id.vacancyFragment -> {
                    bottomNavigationView.isVisible = false
                }

                else -> {
                    bottomNavigationView.isVisible = true
                }
            }
        }
        bottomNavigationView.setupWithNavController(navController)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
