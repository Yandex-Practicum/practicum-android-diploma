package ru.practicum.android.diploma.db.domain.impl

import ru.practicum.android.diploma.db.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.db.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.db.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbInteractorImpl(
    private val vacancyDbRepository: VacancyDbRepository,
    private val vacancyDbConverter: VacancyDbConverter,
) :
    VacancyDbInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        vacancyDbRepository.insertVacancy(vacancyDbConverter.map(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        vacancyDbRepository.deleteVacancy(vacancyDbConverter.map(vacancy))
    }

    override suspend fun getFavouriteVacancy(): List<Vacancy> {
        var vacancies = listOf<Vacancy>()
        vacancyDbRepository.getFavouriteVacancy().collect(){vacanciesEntity ->
            vacancies = vacanciesEntity.map {vacancyEntity -> vacancyDbConverter.map(vacancyEntity)}
        }
        return vacancies
    }
}