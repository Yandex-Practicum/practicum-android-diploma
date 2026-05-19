package ru.practicum.android.diploma.industry.ui

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun IndustryScreen(
    viewModel: IndustryViewModel,
    onBack: () -> Unit
) {
    AppScreen(R.string.industry_screen_title, onBack) {
    }
}
