package ru.practicum.android.diploma.vacancy.ui

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun VacancyScreen(viewModel: VacancyViewModel, onBack: () -> Unit) {
    AppScreen(R.string.vacancy_screen_title, onBack) {
    }
}
