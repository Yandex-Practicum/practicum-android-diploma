package ru.practicum.android.diploma.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateToFilter: () -> Unit,
    onNavigateToVacancy: (id: String) -> Unit
) {
    AppScreen(R.string.search_screen_title) {
        Column {
            Button(
                onClick = onNavigateToFilter,
                content = { Text("to Filter") }
            )

            Button(
                onClick = { onNavigateToVacancy("1") },
                content = { Text("to Vacancy") }
            )
        }
    }
}
