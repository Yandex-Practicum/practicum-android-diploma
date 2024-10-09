package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

interface FavoriteVacancyRepository {

    suspend fun insertVacancy(vacancy: Vacancy)

    suspend fun deleteVacancyById(id: String)

    fun getVacancies(): Flow<List<VacancySearch>>

    fun getVacancyByID(id: String): Flow<Vacancy?>

    suspend fun updateVacancy(vacancy: Vacancy)
}
