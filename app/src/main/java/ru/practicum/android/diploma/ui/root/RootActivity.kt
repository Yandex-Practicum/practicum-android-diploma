package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Подключение NavController к BottomBar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationPanel.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancySearchFragment,
                R.id.favouritesFragment,
                R.id.teamFragment -> {
                    binding.bottomNavigationPanel.visibility = View.VISIBLE
                    binding.flDivider.visibility = View.VISIBLE
                }

                else -> {
                    binding.bottomNavigationPanel.visibility = View.GONE
                    binding.flDivider.visibility = View.GONE
                }
            }
        }
    }
}
