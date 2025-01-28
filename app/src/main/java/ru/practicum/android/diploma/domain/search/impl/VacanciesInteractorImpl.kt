package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

class VacanciesInteractorImpl(
    private val vacanciesRepository: VacanciesRepository
) : VacanciesInteractor {
    override fun searchVacancies(text: String?, options: HashMap<String, Int>): Flow<Resource<VacancyResponse>> {
        return vacanciesRepository.searchVacancies(text, options)
    }
}
