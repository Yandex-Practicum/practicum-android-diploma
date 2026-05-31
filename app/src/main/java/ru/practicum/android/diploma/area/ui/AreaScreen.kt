package ru.practicum.android.diploma.area.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun AreaScreen(
    viewModel: AreaViewModel,
    onNavigateToRegion: () -> Unit,
    onNavigateToCountry: () -> Unit,
    onBack: () -> Unit
) {
    AppScreen(R.string.area_screen_title, onBack) {
        Column {
            Button(
                onClick = onNavigateToRegion,
                content = { Text("to Region") }
            )
            Button(
                onClick = onNavigateToCountry,
                content = { Text("to Country") }
            )
        }
    }
}
