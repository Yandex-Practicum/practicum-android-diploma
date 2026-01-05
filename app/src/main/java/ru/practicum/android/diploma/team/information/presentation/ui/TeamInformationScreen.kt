package ru.practicum.android.diploma.team.information.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16
import ru.practicum.android.diploma.core.presentation.ui.theme.dp8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamInformationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_team_info),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dp16),
        ) {
            Text(
                modifier = Modifier.padding(vertical = dp16),
                text = stringResource(R.string.developers_title),
                style = MaterialTheme.typography.displayLarge
            )

            DevelopersList()
        }
    }
}

@Composable
private fun DevelopersList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dp8)

    ) {
        Text(
            text = stringResource(R.string.dev_gleb),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(R.string.dev_kermen),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(R.string.dev_semyon),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
