package ru.practicum.android.diploma.core.ui.utils

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.navigation.Screen

private data class BottomNavItem(
    val screen: Screen,
    val iconRes: Int,
    val labelRes: Int
)

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.let { destination ->
        !destination.hasRoute(Screen.Vacancy::class) &&
            !destination.hasRoute(Screen.Filter::class) &&
            !destination.hasRoute(Screen.Area::class) &&
            !destination.hasRoute(Screen.Country::class) &&
            !destination.hasRoute(Screen.Region::class) &&
            !destination.hasRoute(Screen.Industry::class)
    } ?: true

    if (showBottomBar) {
        NavigationBar(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            val tabs = listOf(
                BottomNavItem(Screen.Search, R.drawable.ic_search, R.string.search_tab),
                BottomNavItem(Screen.Favorites, R.drawable.ic_favorites, R.string.favorites_tab),
                BottomNavItem(Screen.Team, R.drawable.ic_team, R.string.team_tab)
            )

            tabs.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.screen::class) } == true

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = stringResource(id = item.labelRes)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.labelRes),
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(item.screen) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    }
}
