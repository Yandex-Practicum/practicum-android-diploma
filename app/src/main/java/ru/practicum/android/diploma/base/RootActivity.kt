package ru.practicum.android.diploma.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvBottomNav.setupWithNavController(navController)
        val hideList = listOf(
            R.id.vacancyFragment,
            R.id.filterFragment,
            R.id.locationFragment,
            R.id.countryFragment,
            R.id.regionFragment,
            R.id.sectorFragment
        )
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            binding.bnvBottomNav.isVisible = !hideList.contains(navDestination.id)
        }
    }

}
