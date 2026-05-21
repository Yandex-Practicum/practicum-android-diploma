package ru.practicum.android.diploma.favorites.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.mocks.mockList
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModel
import ru.practicum.android.diploma.favorites.ui.FavoritesViewState

class FavoritesPreviewProvider : PreviewParameterProvider<FavoritesViewModel> {
    override val values = sequenceOf(
        FavoritesViewModelMock(FavoritesViewState.Empty),
        FavoritesViewModelMock(FavoritesViewState.Data(Vacancy.mockList())),
        FavoritesViewModelMock(FavoritesViewState.Error()),
    )
}
