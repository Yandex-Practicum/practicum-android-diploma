package ru.practicum.android.diploma.presentation.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val bottomNavDivider = findViewById<android.view.View>(R.id.bottomNavigationDivider)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancyDetailsFragment,
                R.id.filterFragment,
                R.id.workplaceFragment,
                R.id.selectCountryFragment,
                R.id.selectRegionFragment,
                R.id.industrySelectionFragment -> {
                    bottomNav.visibility = android.view.View.GONE
                    bottomNavDivider.visibility = android.view.View.GONE
                }
                else -> {
                    bottomNav.visibility = android.view.View.VISIBLE
                    bottomNavDivider.visibility = android.view.View.VISIBLE
                }
            }
        }
    }
}
