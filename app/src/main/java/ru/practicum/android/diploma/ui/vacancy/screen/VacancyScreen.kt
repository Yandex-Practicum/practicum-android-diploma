package ru.practicum.android.diploma.ui.vacancy.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.presentation.vacancy.state.VacancyDetailsUiState

@Composable
fun VacancyScreen(
    modifier: Modifier = Modifier,
    state: VacancyDetailsUiState,
    onLoadVacancy: () -> Unit,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onLoadVacancy()
    }

    Box(modifier = modifier)
}
