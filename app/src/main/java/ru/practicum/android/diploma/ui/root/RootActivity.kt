package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_root)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentContainerView = findViewById<FragmentContainerView>(R.id.fragment_container_view)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.API_ACCESS_TOKEN)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        WindowCompat.setDecorFitsSystemWindows(window, false)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.jobSearchFragment, R.id.favoritesFragment, R.id.teamFragment -> {
                    bottomNavigationView.isVisible = true
                }

                else -> bottomNavigationView.isVisible = false
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainerView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                0,
                systemBars.top,
                0,
                if (!bottomNavigationView.isVisible) systemBars.bottom else 0
            )

            insets
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
