package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment, R.id.favouriteFragment, R.id.teamFragment -> {
                    binding.bottomNavigationView.isVisible = true
                }

                R.id.detailFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
            }
        }
        //Пример использования access token для HeadHunter API
        // networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

    }

    /* private fun networkRequestExample(accessToken: String) {
         // ...
     } */

}