package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.core.domain.mocks.mockList
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState
import ru.practicum.android.diploma.search.ui.mock.SearchViewModelMock

val searchModule = module {
    viewModel<SearchViewModel> {
        // SearchViewModelImpl() //TODO temporary mock
        SearchViewModelMock(mockState = SearchViewState.Data(Vacancy.mockList()))
    }
}
