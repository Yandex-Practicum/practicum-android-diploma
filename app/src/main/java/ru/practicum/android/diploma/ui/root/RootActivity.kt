package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareNavHostFragment()
        prepareNavHostController()
        prepareBottomNavView()
        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
    }

    private fun prepareBinding() {
        binding = ActivityRootBinding.inflate(layoutInflater)
        binding?.let {
            setContentView(it.root)
        }
    }
    private fun prepareNavHostFragment() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    private fun prepareNavHostController() {
        navHostFragment?.let {
            navController = it.navController
        }
    }
    private fun prepareBottomNavView() {
        binding?.let {
            bottomNavigationView = it.bottomNavigation
            navController?.let {
                bottomNavigationView?.setupWithNavController(it)
                it.navInflater
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }
    fun bottomNavigationVisibility(isVisibile: Boolean) {
        binding?.bottomNavigation?.isVisible = isVisibile
    }
}
