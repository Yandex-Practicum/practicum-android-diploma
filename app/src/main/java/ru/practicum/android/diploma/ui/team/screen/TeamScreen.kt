package ru.practicum.android.diploma.ui.team.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.team.component.TeamBottomNavigation
import ru.practicum.android.diploma.ui.team.component.TeamHeader
import ru.practicum.android.diploma.ui.team.component.TeamInfoCard
import ru.practicum.android.diploma.ui.team.component.TeamMemberCard
import ru.practicum.android.diploma.ui.team.model.TeamBottomTab
import ru.practicum.android.diploma.ui.team.model.TeamMember
import ru.practicum.android.diploma.ui.team.rememberTeamMembers
import ru.practicum.android.diploma.ui.team.theme.TeamColors
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun TeamScreen(
    showBottomNavigation: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val members = rememberTeamMembers()
    var selectedTab by remember { mutableStateOf(TeamBottomTab.Team) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TeamColors.ScreenBackgroundSolid,
        bottomBar = {
            if (showBottomNavigation) {
                TeamBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TeamColors.screenBackgroundBrush)
                .padding(innerPadding),
        ) {
            TeamScreenContent(members = members)
        }
    }
}

@Composable
fun TeamScreenContent(
    members: List<TeamMember>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item(key = "header") {
            TeamHeader()
        }
        items(
            items = members,
            key = { it.name },
        ) { member ->
            TeamMemberCard(
                modifier = Modifier.padding(bottom = 12.dp),
                member = member,
            )
        }
        item(key = "footer_tagline") {
            TeamInfoCard(
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TeamScreenPreview() {
    AppTheme {
        TeamScreen()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun TeamScreenContentPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TeamColors.screenBackgroundBrush),
        ) {
            TeamScreenContent(members = rememberTeamMembers())
        }
    }
}
