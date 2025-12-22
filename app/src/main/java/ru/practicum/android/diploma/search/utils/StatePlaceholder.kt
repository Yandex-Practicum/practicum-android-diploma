package ru.practicum.android.diploma.search.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme

@Composable
fun PlaceHolder(
    modifier: Modifier = Modifier,
    @DrawableRes placeholderImage: Int,
    @StringRes placeholderText: Int = 0
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(placeholderImage),
            contentDescription = null,
            modifier = modifier
                .size(width = 328.dp, height = 223.dp)
                .padding(top = 102.dp),
        )

        Text(
            text = stringResource(placeholderText),
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center,
//            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 26.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 22.sp
        )
    }
}

@Preview
@Composable
private fun PlaceholderPreview() {
    VacancySearchAppTheme {
        PlaceHolder(
            placeholderText = R.string.nav_team_info,
            placeholderImage = R.drawable.ic_nav_team_info
        )
    }
}
