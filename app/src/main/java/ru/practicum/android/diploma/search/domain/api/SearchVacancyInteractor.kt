package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacancySearch

interface SearchVacancyInteractor {

    fun getVacancyList(
       query: HashMap<String, String>
    ): Flow<List<VacancySearch>?>

}
