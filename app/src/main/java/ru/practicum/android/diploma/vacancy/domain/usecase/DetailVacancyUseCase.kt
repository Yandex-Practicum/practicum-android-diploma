package ru.practicum.android.diploma.vacancy.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.DetailVacancyRepository

class DetailVacancyUseCase(private val detailVacancyRepository: DetailVacancyRepository) {
    fun execute(id: Long): Flow<Resource<DetailVacancy>> {
        return detailVacancyRepository.getDetailVacancyById(
            id = id
        )
    }
}
