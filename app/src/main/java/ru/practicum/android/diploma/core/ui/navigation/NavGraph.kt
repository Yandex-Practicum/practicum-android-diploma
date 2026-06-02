package ru.practicum.android.diploma.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.area.ui.AreaScreen
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.country.ui.CountryScreen
import ru.practicum.android.diploma.favorites.ui.FavoritesScreen
import ru.practicum.android.diploma.filter.ui.FilterScreen
import ru.practicum.android.diploma.industry.ui.IndustryScreen
import ru.practicum.android.diploma.region.ui.RegionScreen
import ru.practicum.android.diploma.search.ui.SearchScreen
import ru.practicum.android.diploma.team.ui.TeamScreen
import ru.practicum.android.diploma.vacancy.ui.VacancyScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Search) {
        composable<Screen.Search> {
            SearchScreen(
                koinViewModel(),
                onNavigateToFilter = {
                    navController.navigate(Screen.Filter)
                },
                onNavigateToVacancy = {
                    navController.navigate(Screen.Vacancy(it))
                }
            )
        }
        composable<Screen.Filter> {
            FilterScreen(
                koinViewModel(),
                onNavigateToArea = {
                    navController.navigate(Screen.Area)
                },
                onNavigateToIndustry = {
                    navController.navigate(Screen.Industry)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<Screen.Vacancy> { backStackEntry ->
            val vacancy: Screen.Vacancy = backStackEntry.toRoute()
            VacancyScreen(
                koinViewModel { parametersOf(vacancy.id) },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Area> {
            AreaScreen(
                navController.currentBackStackEntry,
                koinViewModel(),
                onNavigateToRegion = {
                    navController.navigate(Screen.Region)
                },
                onNavigateToCountry = {
                    navController.navigate(Screen.Country)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Country> {
            CountryScreen(
                koinViewModel(),
                onBack = { country ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("country", country)
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Region> {
            RegionScreen(
                koinViewModel(),
                onSelect = { region, country ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("region", region)
                    navController.previousBackStackEntry?.savedStateHandle?.set("region_country", country)
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Industry> {
            IndustryScreen(
                koinViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.Favorites> {
            FavoritesScreen(
                koinViewModel(),
                onNavigateToVacancy = {
                    navController.navigate(Screen.Vacancy(it))
                }
            )
        }

        composable<Screen.Team> {
            TeamScreen()
        }
    }

}
