package ru.practicum.android.diploma.ui.favorites.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorites.state.FavoritesUiState
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.common.search.VacancyItem
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    state: FavoritesUiState,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.favorite_vacancies_title),
                navIconVisible = false,
                endFirstIconVisible = false,
                endSecondIconVisible = false
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    FavoritesUiState.Loading -> Loader(modifier = modifier.fillMaxSize())

                    FavoritesUiState.Empty -> PlaceholderLayout(
                        imageRes = R.drawable.img_no_favorite_vacancies,
                        textRes = R.string.no_favorite_vacancies
                    )

                    FavoritesUiState.Error -> PlaceholderLayout(
                        imageRes = R.drawable.img_nothing_found,
                        textRes = R.string.no_vacancies_error,
                    )

                    is FavoritesUiState.Content -> FavoriteVacanciesList(
                        vacancies = state.vacancies,
                        onVacancyClick = onVacancyClick,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = Dimens.ScreenHorizontalPadding,
                                top = 8.dp,
                                end = Dimens.ScreenHorizontalPadding,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteVacanciesList(
    vacancies: List<Vacancy>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = vacancies,
            key = { it.id }
        ) { vacancy ->
            VacancyItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                vacancy,
                onClick = onVacancyClick
            )
        }
    }
}
