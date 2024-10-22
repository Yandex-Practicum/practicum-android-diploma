package ru.practicum.android.diploma.vacancy.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.model.Vacancy

internal interface VacancyDetailInteractor {
    fun getVacancyNetwork(id: String): Flow<Pair<Vacancy?, String?>>
    fun getVacancyDb(id: Int): Flow<Pair<Vacancy?, String?>>
    fun checkVacancyExists(id: Int): Flow<Pair<Int?, String?>>
    fun addVacancy(vacancy: Vacancy): Flow<Pair<Long?, String?>>
    fun deleteVacancy(id: Int): Flow<Pair<Int?, String?>>
    fun share(shareLink: String)
}
