package ru.practicum.android.diploma.team.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun TeamScreen() {
    val items = stringArrayResource(R.array.team_developers)
    AppScreen(R.string.team_screen_title) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.padding16)
        ) {
            Text(
                stringResource(R.string.team_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(top = Dimens.padding24, bottom = Dimens.padding32)
            )
            items.forEach { string ->
                Text(
                    string,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = Dimens.padding16)
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun TeamScreenPreview() {
    AppTheme {
        TeamScreen()
    }
}
