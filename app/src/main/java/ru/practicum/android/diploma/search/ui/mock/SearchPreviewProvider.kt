package ru.practicum.android.diploma.search.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.preview.mockList
import ru.practicum.android.diploma.search.ui.SearchError
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchScreenState

class SearchPreviewProvider : PreviewParameterProvider<SearchViewModel> {
    override val values = sequenceOf(
        SearchViewModelMock(SearchScreenState.Default),
        SearchViewModelMock(SearchScreenState.Default, isFiltered = true, query = "123"),
        SearchViewModelMock(SearchScreenState.Loading, query = "123"),
        SearchViewModelMock(
            SearchScreenState.Content(
                Vacancy.mockList(),
                totalFound = Vacancy.mockList().count()
            ),
            query = "123"
        ),
        SearchViewModelMock(SearchScreenState.Error(SearchError.INTERNET), query = "123"),
        SearchViewModelMock(SearchScreenState.Error(SearchError.NOT_FOUND), query = "123"),
    )
}
