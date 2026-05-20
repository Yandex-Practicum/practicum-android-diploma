package ru.practicum.android.diploma.region.ui

import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun RegionScreen(viewModel: RegionViewModel, onBack: () -> Unit) {
    AppScreen(R.string.region_screen_title, onBack) {
    }
}
