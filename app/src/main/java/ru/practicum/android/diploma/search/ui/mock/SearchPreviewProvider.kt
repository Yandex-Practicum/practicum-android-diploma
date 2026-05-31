package ru.practicum.android.diploma.search.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.preview.mockList
import ru.practicum.android.diploma.search.ui.SearchError
import ru.practicum.android.diploma.search.ui.SearchScreenState
import ru.practicum.android.diploma.search.ui.SearchViewModel

class SearchPreviewProvider : PreviewParameterProvider<SearchViewModel> {
    override val values = sequenceOf(
        SearchViewModelMock(SearchScreenState.Initial),
        SearchViewModelMock(SearchScreenState.Initial, isFiltered = true, query = "123"),
        SearchViewModelMock(SearchScreenState.Loading, query = "123"),
        SearchViewModelMock(
            SearchScreenState.Content(
                Vacancy.mockList(),
                totalFound = Vacancy.mockList().count(),
                false,
            ),
            query = "123"
        ),
        SearchViewModelMock(
            SearchScreenState.Content(
                Vacancy.mockList(),
                totalFound = Vacancy.mockList().count(),
                true,
            ),
            query = "123"
        ),
        SearchViewModelMock(
            SearchScreenState.Content(
                Vacancy.mockList(),
                totalFound = Vacancy.mockList().count(),
                false
            ),
            query = "123",
            errorCode = SearchError.NO_INTERNET
        ),
        SearchViewModelMock(SearchScreenState.Error(SearchError.NO_INTERNET), query = "123"),
        SearchViewModelMock(SearchScreenState.Error(SearchError.EMPTY_RESULTS), query = "123"),
        SearchViewModelMock(SearchScreenState.Error(SearchError.SERVER_ERROR), query = "123"),
    )
}
