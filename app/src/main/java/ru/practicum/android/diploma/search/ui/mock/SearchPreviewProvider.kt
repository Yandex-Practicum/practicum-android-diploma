package ru.practicum.android.diploma.search.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.mocks.mockList
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.SearchViewError
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState

class SearchPreviewProvider : PreviewParameterProvider<SearchViewModel> {
    override val values = sequenceOf(
        SearchViewModelMock(SearchViewState.Default),
        SearchViewModelMock(SearchViewState.Default, isFiltered = true, query = "123"),
        SearchViewModelMock(SearchViewState.Loading, query = "123"),
        SearchViewModelMock(SearchViewState.Data(Vacancy.mockList()), query = "123"),
        SearchViewModelMock(SearchViewState.Error(SearchViewError.Internet), query = "123"),
        SearchViewModelMock(SearchViewState.Error(SearchViewError.NotFound), query = "123"),
    )
}
