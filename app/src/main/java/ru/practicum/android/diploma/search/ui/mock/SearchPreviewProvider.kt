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
        SearchViewModelMock(SearchScreenState.Initial, initialIsFiltered = true, initialQuery = "123"),
        SearchViewModelMock(SearchScreenState.Loading, initialQuery = "123"),
        SearchViewModelMock(
            SearchScreenState.Content(Vacancy.mockList(), Vacancy.mockList().size),
            initialQuery = "123"
        ),
            SearchViewModelMock(SearchScreenState.Error(SearchError.INTERNET), initialQuery = "123"),
            SearchViewModelMock(SearchScreenState.Error(SearchError.NOT_FOUND), initialQuery = "123")
        )
}
