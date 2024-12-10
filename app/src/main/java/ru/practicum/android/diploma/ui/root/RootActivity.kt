package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {

//                R.id.searchFragment -> {
//                    bottomNavigationView.visibility = View.GONE
//                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }

            }
        }

        // Пример использования access token для HeadHunter API
        //networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    //private fun networkRequestExample(accessToken: String) {
        // ...
    //}

}
