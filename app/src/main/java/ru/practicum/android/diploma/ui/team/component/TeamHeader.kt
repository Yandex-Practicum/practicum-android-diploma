package ru.practicum.android.diploma.ui.team.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.team.theme.TeamColors
import ru.practicum.android.diploma.ui.team.theme.TeamTypography

@Composable
fun TeamHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 20.dp),
    ) {
        Text(
            text = stringResource(R.string.team),
            style = TeamTypography.ScreenTitle,
            color = TeamColors.PrimaryBlue,
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(R.string.team_screen_headline),
            style = TeamTypography.MainTitle,
            color = TeamColors.PrimaryText,
        )
    }
}
