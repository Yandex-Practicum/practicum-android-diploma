package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.utils.ResponseData
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class VacancyInteractorImpl(private val vacancyRepository: VacancyRepository) : VacancyInteractor {

    override fun getVacancy(id: Int): Flow<ResponseData<VacancyFull>> {
        return vacancyRepository.getVacancy(id)
    }
}
