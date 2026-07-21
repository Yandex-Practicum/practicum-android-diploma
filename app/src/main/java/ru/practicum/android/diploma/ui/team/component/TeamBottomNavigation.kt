package ru.practicum.android.diploma.ui.team.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.ui.team.model.TeamBottomTab
import ru.practicum.android.diploma.ui.team.theme.TeamColors
import ru.practicum.android.diploma.ui.theme.Regular12

@Composable
fun TeamBottomNavigation(
    selectedTab: TeamBottomTab,
    onTabSelected: (TeamBottomTab) -> Unit,
) {
    NavigationBar(
        containerColor = TeamColors.CardBackground,
    ) {
        TeamBottomTab.entries.forEach { tab ->
            val selected = tab == selectedTab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        painter = painterResource(tab.iconResId),
                        contentDescription = stringResource(tab.titleResId),
                    )
                },
                label = {
                    Text(
                        text = stringResource(tab.titleResId),
                        style = Regular12,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = TeamColors.Accent,
                    selectedTextColor = TeamColors.Accent,
                    unselectedIconColor = TeamColors.BottomNavInactive,
                    unselectedTextColor = TeamColors.BottomNavInactive,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }
}
