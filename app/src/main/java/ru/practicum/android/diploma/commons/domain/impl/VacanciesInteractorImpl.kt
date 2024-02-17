package ru.practicum.android.diploma.commons.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commons.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.commons.domain.api.VacanciesRepository
import ru.practicum.android.diploma.commons.domain.model.VacanciesModel
import ru.practicum.android.diploma.commons.domain.model.VacancyModel

class VacanciesInteractorImpl(
    private val repository: VacanciesRepository
) : VacanciesInteractor {

    override fun searchVacancies(
        name: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>> {
        return repository.searchVacancies(name, page, amount)
    }

    override fun searchSimilarVacancies(
        id: String,
        page: Long,
        amount: Long
    ): Flow<Pair<VacanciesModel, Int>> {
        return repository.searchSimilarVacancies(id, page, amount)
    }

    override fun searchConcreteVacancy(
        id: String
    ): Flow<Pair<VacancyModel?, Int>> {
        return repository.searchConcreteVacancy(id)
    }

}
