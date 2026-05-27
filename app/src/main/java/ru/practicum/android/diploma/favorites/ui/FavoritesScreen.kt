package ru.practicum.android.diploma.favorites.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.Stub
import ru.practicum.android.diploma.core.ui.utils.VacancyList
import ru.practicum.android.diploma.favorites.ui.mock.FavoritesPreviewProvider

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onNavigateToVacancy: (id: String) -> Unit
) {
    val state = viewModel.state.collectAsState()

    AppScreen(R.string.favorites_screen_title) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when (state.value) {
                is FavoritesViewState.Empty -> {
                    Stub(R.drawable.image_favorites_stub_empty, R.string.favorites_empty)
                }
                is FavoritesViewState.Data -> {
                    val content = (state.value as FavoritesViewState.Data).vacancies
                    VacancyList(
                        content,
                        modifier = Modifier.padding(top = Dimens.padding16)
                    ) { vacancy ->
                        onNavigateToVacancy(vacancy.id)
                    }
                }
                is FavoritesViewState.Error -> {
                    Stub(
                        R.drawable.image_core_stub_not_found,
                        R.string.favorites_error_not_found
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(FavoritesPreviewProvider::class) model: FavoritesViewModel
) {
    AppTheme {
        FavoritesScreen(
            model,
            onNavigateToVacancy = {}
        )
    }
}
