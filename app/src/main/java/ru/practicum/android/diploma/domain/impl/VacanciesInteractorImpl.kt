package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesInteractorImpl(): VacanciesInteractor {

    val repo = VacanciesRepositoryImpl()

    override fun searchVacancies(page: Int): Flow<List<Vacancy>> {
        //TODO добавить проверку состояния
        return repo.searchVacancies(page)
    }
}
