package ru.practicum.android.diploma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.navigate.api.NavigateArgsToVacancy
import ru.practicum.android.diploma.navigate.observable.Navigate
import ru.practicum.android.diploma.navigate.state.NavigateEventState

class RootActivity : AppCompatActivity() {

    private val navigate: Navigate<NavigateEventState> by inject()
    private val navigateArgsToVacancy: NavigateArgsToVacancy<NavigateEventState> by inject()

    private var _binding: ActivityRootBinding? = null
    private val binding: ActivityRootBinding get() = _binding!!

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.rootFragments) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search_fragment, R.id.team_navigation, R.id.favorites_fragment, R.id.root_navigation,
                ru.practicum.android.diploma.team.R.id.teamFragment,
                -> {
                    binding.bottomNavigationMenu.isVisible = true
                    binding.navigationPanelDivider.isVisible = true
                }

                else -> {
                    binding.bottomNavigationMenu.isVisible = false
                    binding.navigationPanelDivider.isVisible = false
                }
            }
        }

        binding.bottomNavigationMenu.setupWithNavController(navController)

    }

    override fun onStart() {
        super.onStart()
        navigate.observeNavigateEventState(this) { state ->
            navigate(state)
        }
    }

    private fun navigate(state: NavigateEventState) {
        val actionId = when (state) {
            is NavigateEventState.ToVacancyDataSourceNetwork ->
                R.id.action_searchFragment_to_vacancyFragment
            is NavigateEventState.ToVacancyDataSourceDb ->
                R.id.action_favoritesFragment_to_vacancyFragment
            is NavigateEventState.ToFilter ->
                R.id.action_search_fragment_to_filter_navigation
        }
        navController.navigate(actionId, navigateArgsToVacancy.createArgs(state))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
