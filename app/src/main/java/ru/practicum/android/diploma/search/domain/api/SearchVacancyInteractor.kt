package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacancyListResponseData
import ru.practicum.android.diploma.util.network.HttpStatusCode

interface SearchVacancyInteractor {

    fun getVacancyList(
        query: HashMap<String, String>
    ): Flow<Pair<VacancyListResponseData?, HttpStatusCode?>>

}
