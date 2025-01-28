package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.containerView.id) as NavHostFragment

        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filterCommonFragment,
                R.id.filterCountryRegionFragment,
                R.id.filterCountryFragment,
                R.id.filterRegionFragment,
                R.id.filterIndustryFragment,
                R.id.vacancyFragment -> {
                    binding.bottomNavigationView.isVisible = false
                    binding.divider.isVisible = false
                }

                else -> {
                    binding.bottomNavigationView.isVisible = true
                    binding.divider.isVisible = true
                }
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
