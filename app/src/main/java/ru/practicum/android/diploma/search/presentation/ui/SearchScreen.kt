package ru.practicum.android.diploma.search.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R

@Composable
fun SearchScreen(
    onOpenVacancyDetails: () -> Unit,
    onOpenFilters: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = stringResource(id = R.string.title_search))

            Button(onClick = onOpenVacancyDetails) {
                Text(text = "Переход на экран вакансии")
            }

            Button(onClick = onOpenFilters) {
                Text(text = "Переход на экран фильтров")
            }
        }
    }
}
