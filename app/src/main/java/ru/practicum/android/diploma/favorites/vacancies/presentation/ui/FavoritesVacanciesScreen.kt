package ru.practicum.android.diploma.favorites.vacancies.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.components.VacanciesList
import ru.practicum.android.diploma.core.presentation.ui.model.VacancyListItemUi
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16
import ru.practicum.android.diploma.core.presentation.ui.util.PlaceHolder
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.presentation.viewmodel.FavoritesVacanciesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesVacanciesScreen(
    onOpenVacancyDetails: (String) -> Unit,
    viewModel: FavoritesVacanciesViewModel = koinViewModel(),
) {
    val favorites by viewModel.favorites.collectAsState()
    val isError by viewModel.isError.collectAsState()

    val vacancies = favorites.map { it.toVacancyListItemUi() }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dp16),
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_favorites),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            )

            when {
                isError -> {
                    PlaceHolder(
                        placeholderImage = R.drawable.get_items_error_placeholder,
                        placeholderText = R.string.get_vacancies_error,
                    )
                }

                vacancies.isEmpty() -> {
                    PlaceHolder(
                        placeholderImage = R.drawable.empty_favorites_placeholder,
                        placeholderText = R.string.favorites_list_empty,
                    )
                }

                else -> {
                    VacanciesList(
                        vacancies = vacancies,
                        onVacancyClick = onOpenVacancyDetails,
                    )
                }
            }
        }
    }
}

private fun FavoriteVacancyEntity.toVacancyListItemUi(): VacancyListItemUi =
    VacancyListItemUi(
        id = id,
        logoUrl = employerLogoUrl,
        vacancyName = name,
        city = city,
        employer = employerName,
        salary = null,//доделать форматер для зарплаты
    )

