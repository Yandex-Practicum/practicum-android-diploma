package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16
import ru.practicum.android.diploma.core.presentation.ui.theme.dp30

@Composable
fun PlaceHolder(
    @DrawableRes placeholderImage: Int,
    @StringRes placeholderText: Int = 0
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dp16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(placeholderImage),
            contentDescription = null,
        )

        Text(
            text = stringResource(placeholderText),
            modifier = Modifier
                .padding(top = dp16)
                .padding(horizontal = dp30),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceholderPreview() {
    VacancySearchAppTheme {
        PlaceHolder(
            placeholderText = R.string.nav_team_info,
            placeholderImage = R.drawable.ic_nav_team_info
        )
    }
}
