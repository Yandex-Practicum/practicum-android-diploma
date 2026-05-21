package ru.practicum.android.diploma.favorites.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onNavigateToVacancy: (id: String) -> Unit
) {
    AppScreen(R.string.filter_screen_title) {
        Column {
            Button(
                onClick = { onNavigateToVacancy("2") },
                content = { Text("to Vacancy") }
            )

        }
    }
}
