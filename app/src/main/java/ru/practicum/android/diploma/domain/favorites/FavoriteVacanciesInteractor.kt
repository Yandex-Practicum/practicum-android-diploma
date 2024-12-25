package ru.practicum.android.diploma.domain.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoriteVacanciesInteractor {

    suspend fun insertFavoriteVacancy(vacancyForInsert: Vacancy)

    suspend fun deleteFavoriteVacancy(vacancyForDeleteId: String)

    fun getFavoriteVacancies(): Flow<List<Vacancy>?>

    fun getFavoriteVacancyById(vacancyId: String): Flow<Vacancy?>

}
