package ru.practicum.android.diploma.vacancy.details.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor

class VacancyDetailsInteractorImpl(
    private val searchInteractor: SearchInteractor
) : VacancyDetailsInteractor {
    override fun getVacancyById(id: String): Flow<Result<VacancyDetail>> {
        return searchInteractor.getVacancyById(id)
    }
}
