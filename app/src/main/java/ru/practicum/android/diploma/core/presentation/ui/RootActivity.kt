package ru.practicum.android.diploma.core.presentation.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        // пока что сделал таким образом, что если находимся на экране избранного или команды,
        // то нажатие системной "назад" ведет на экран поиска, который считается стартовым/главным
        // с экрана поиска системная "назад" просто закрывает приложение
        onBackPressedDispatcher.addCallback(this) {
            val currentDestinationId = navController.currentDestination?.id
            when (currentDestinationId) {
                R.id.menu_favorites, R.id.menu_team_info -> {
                    navController.navigate(R.id.menu_main)
                }
                else -> {
                    finish()
                }
            }
        }
    }
}
