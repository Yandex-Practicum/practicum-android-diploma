package ru.practicum.android.diploma.ui.search

import androidx.paging.PagingData
import ru.practicum.android.diploma.domain.models.Vacancy

data class SearchViewState(
    val state: SearchState? = null,
    var foundVacancies: String? = null,
    val findVacancyPagination: PagingData<Vacancy> = PagingData.empty()
)
