package ru.practicum.android.diploma.ui.main

import androidx.paging.PagingData
import ru.practicum.android.diploma.domain.models.Vacancy

data class MainViewState (
    val state: SearchState? = null,
    var foundVacancies: String? = null,
    val findVacancyPagination: PagingData<Vacancy> = PagingData.empty()
)
