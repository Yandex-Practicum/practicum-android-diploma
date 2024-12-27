package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding?.bottomNavigationView
        bottomNavigationView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancyFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }
                R.id.choiceIndustryFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }
                R.id.filterSettingsFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }

                R.id.choiceIndustryFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }

                R.id.choiceRegionFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }

                R.id.choiceCountryFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }

                R.id.choiceWorkplaceFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView?.visibility = View.VISIBLE
                }
            }
        }
    }
}
