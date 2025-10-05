package ru.practicum.android.diploma.domain.usecase

import ru.practicum.android.diploma.domain.repository.VacancyRepository

class GetVacanciesUseCase(private val repository: VacancyRepository) {
    suspend operator fun invoke(): List<ru.practicum.android.diploma.domain.models.Vacancy> {
        return repository.getVacancies()
    }
}
