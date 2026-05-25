package ru.practicum.android.diploma.filter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.utils.AppScreen

@Composable
fun FilterScreen(
    viewModel: FilterViewModel,
    onNavigateToArea: () -> Unit,
    onNavigateToIndustry: () -> Unit,
    onBack: () -> Unit
) {
    AppScreen(R.string.country_screen_title, onBack) {
        Column {
            Button(
                onClick = onNavigateToArea,
                content = { Text("to Area") }
            )

            Button(
                onClick = onNavigateToIndustry,
                content = { Text("to Industry") }
            )

        }
    }
}
