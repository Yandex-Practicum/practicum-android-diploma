package ru.practicum.android.diploma.base

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null

    var onFilterClickListener: OnFilterClickListener? = null
        set(value) {
            field = value
            binding?.apply {
                ivFilter.setOnClickListener {
                    onFilterClickListener?.onClick()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.apply {
            val appBarConfiguration = AppBarConfiguration(bnvBottomNav.menu)
            toolbar.apply {
                setupWithNavController(navController, appBarConfiguration)
                setNavigationOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            bnvBottomNav.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, navDestination, _ ->
                onDestinationChanged(navDestination.id)
            }

            // Пример использования access token для HeadHunter API
            networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        }
    }

    private fun onDestinationChanged(@NavigationRes destinationId: Int) {
        binding?.apply {
            bnvBottomNav.isVisible = when (destinationId) {
                R.id.searchFragment, R.id.favoritesFragment, R.id.teamFragment -> true
                else -> false
            }

            toolbar.title = getString(
                when (destinationId) {
                    R.id.countryFragment -> R.string.country_title
                    R.id.favoritesFragment -> R.string.favorites_title
                    R.id.filterFragment -> R.string.filter_title
                    R.id.locationFragment -> R.string.location_title
                    R.id.regionFragment -> R.string.region_title
                    R.id.searchFragment -> R.string.search_title
                    R.id.sectorFragment -> R.string.sector_title
                    R.id.teamFragment -> R.string.team_title
                    else -> R.string.vacancy_title
                }
            )

            if (destinationId == R.id.searchFragment) {
                ivFilter.isVisible = true
            } else {
                ivFilter.isVisible = false
                ivFilter.setOnClickListener(null)
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    fun interface OnFilterClickListener {
        fun onClick()
    }
}
