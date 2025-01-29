package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.api.VacancyRepository
import ru.practicum.android.diploma.domain.models.Page
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyInteractorImpl(
    private val vacancyRepository: VacancyRepository
) : VacancyInteractor {
    override fun getVacancies(options: Map<String, String>): Flow<Resource<Page>> {
        return vacancyRepository.getVacancies(options)
    }

    override suspend fun getVacancy(vacancyId: String): Resource<Vacancy> {
        return vacancyRepository.getVacancy(vacancyId)
    }

}
