package ru.practicum.android.diploma.country.ui

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun CountryScreen(viewModel: CountryViewModel, onBack: () -> Unit) {
    AppScreen(R.string.country_screen_title, onBack) {
    }
}
