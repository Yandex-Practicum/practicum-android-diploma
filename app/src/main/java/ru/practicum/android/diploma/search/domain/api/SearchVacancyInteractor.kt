package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacancySearch

interface SearchVacancyInteractor {

    fun getVacancyList(
        page: Int = 0,
        text: String,
        industry: String? = null,
        salary: Int? = null,
        onlyWithSalary: Boolean = false
    ): Flow<List<VacancySearch>?>

}
