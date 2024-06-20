package ru.practicum.android.diploma.domain.favorites

interface FavoritesVacancyInteractor {
    suspend fun getAllFavoritesVacancy(): Flow<List<Vacancy>?>
    suspend fun getOneFavoriteVacancy(vacancyId: String): Vacancy
    suspend fun deleteFavoriteVacancy(vacancy: Vacancy): Int
    suspend fun insertFavoriteVacancy(vacancy: Vacancy)
    suspend fun getFavoriteIds(): List<String>

}
