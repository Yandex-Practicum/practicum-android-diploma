package ru.practicum.android.diploma.data


import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.FavouriteVacancyDbConvertor
import ru.practicum.android.diploma.domain.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favouriteVacancyDbConvertor: FavouriteVacancyDbConvertor
) : FavouritesRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        val vacancyEntity = favouriteVacancyDbConvertor.mapVacancy(vacancy)
        appDatabase.getFavoriteVacancyDao().saveVacancy(vacancyEntity)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        val vacancyEntity = favouriteVacancyDbConvertor.mapVacancy(vacancy)
        appDatabase.getFavoriteVacancyDao().deleteVacancy(vacancyEntity)
    }

    override fun getVacancies(): Flow<List<Vacancy>> = flow {
        appDatabase.getFavoriteVacancyDao().getVacancies().collect { vacancyEntity ->
            emit(convertFromEntity(vacancyEntity))
        }
    }.flowOn(Dispatchers.IO)

    private fun convertFromEntity(vacancies: List<FavoriteVacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> favouriteVacancyDbConvertor.mapVacancy(vacancy) }
    }

}
