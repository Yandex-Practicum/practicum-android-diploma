package ru.practicum.android.diploma.ui.favorites.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.state.FavoritesUiState
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.search.VacancyItem

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        FavoritesUiState.Loading -> Loader(modifier = modifier.fillMaxSize())

        FavoritesUiState.Empty -> PlaceholderLayout(
            imageRes = R.drawable.img_nothing_found,
        )

        FavoritesUiState.Error -> PlaceholderLayout(
            imageRes = R.drawable.img_no_internet,
            textRes = R.string.no_vacancies_error,
        )

        is FavoritesUiState.Content -> FavoritesContent(
            vacancies = state.vacancies,
            onVacancyClick = onVacancyClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun FavoritesContent(
    vacancies: List<Vacancy>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(
            items = vacancies,
            key = { it.id },
        ) { vacancy ->
            VacancyItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                vacancy = vacancy,
                onClick = onVacancyClick,
            )
        }
    }
}
