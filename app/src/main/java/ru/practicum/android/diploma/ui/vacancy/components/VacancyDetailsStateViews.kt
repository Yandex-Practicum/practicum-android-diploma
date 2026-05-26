package ru.practicum.android.diploma.ui.vacancy.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout

@Composable
fun VacancyDetailsLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Loader()
    }
}

@Composable
fun VacancyDetailsError(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    @StringRes messageRes: Int,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PlaceholderLayout(
            imageRes = imageRes,
            textRes = messageRes,
        )
    }
}
