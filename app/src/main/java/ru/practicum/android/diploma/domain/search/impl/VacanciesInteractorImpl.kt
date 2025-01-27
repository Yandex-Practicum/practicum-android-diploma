package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

class VacanciesInteractorImpl(
    private val vacanciesRepository: VacanciesRepository
) : VacanciesInteractor {
    override fun searchVacancies(text: String?): Flow<Resource<List<Vacancy>>> {
        return vacanciesRepository.searchVacancies(text)
    }
}
