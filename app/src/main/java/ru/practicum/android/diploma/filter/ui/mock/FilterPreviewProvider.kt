package ru.practicum.android.diploma.filter.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.mocks.mockList
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.filter.ui.FilterScreenState
import ru.practicum.android.diploma.filter.ui.FilterViewModel
import ru.practicum.android.diploma.search.ui.SearchViewError
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState

class FilterPreviewProvider : PreviewParameterProvider<FilterViewModel> {
    override val values = sequenceOf(
        FilterViewModelMock(FilterScreenState()),
            FilterViewModelMock(FilterScreenState(industry = "IT")),
        FilterViewModelMock(FilterScreenState(industry = "IT", area = "Москва")),
        FilterViewModelMock(FilterScreenState(industry = "IT", area = "Москва", salary = "500"))
        ,
        FilterViewModelMock(FilterScreenState(industry = "IT", area = "Москва", salary = "500", onlyWithSalary = true))
    )
}
