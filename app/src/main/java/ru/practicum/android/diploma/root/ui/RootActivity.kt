package ru.practicum.android.diploma.root.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.network.HttpStatusCode

class RootActivity : AppCompatActivity() {

    val filterAreaInteractor by inject<FilterAreaInteractor>()

    private var countryList = emptyList<Area>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCountryList()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancySearchFragment,
                R.id.favoriteFragment,
                R.id.teamInfoFragment -> bottomNavigationView.isVisible = true

                else -> bottomNavigationView.isVisible = false
            }
        }
    }

    fun getCountryList(): List<Area> {
        if (countryList.isEmpty()) {
            lifecycleScope.launch {
                filterAreaInteractor
                    .getCountries()
                    .collect { pair ->
                        countryList = processResult(pair.first, pair.second)
                    }
            }
        }

        return countryList
    }

    private fun processResult(foundAreas: List<Area>?, errorMessage: HttpStatusCode?): List<Area> {
        return when {
            errorMessage == HttpStatusCode.NOT_CONNECTED -> {
                emptyList<Area>()
            }

            foundAreas.isNullOrEmpty() -> {
                emptyList<Area>()
            }

            else -> {
                foundAreas
            }
        }
    }
}
