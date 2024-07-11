package ru.practicum.android.diploma.favourites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.favourites.data.db.MainDB
import ru.practicum.android.diploma.favourites.data.db.toVacancy
import ru.practicum.android.diploma.favourites.data.db.toVacancyEntity
import ru.practicum.android.diploma.favourites.data.db.toVacancyFull
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class FavouritesRepositoryImpl(private val db: MainDB) : FavouritesRepository {

    override fun favouriteVacancies(): Flow<List<Vacancy>> {
        return db.favouritesDao().getAll().map { list ->
            list.map { it.toVacancy() }
        }
    }

    override fun favouriteIds(): Flow<List<Int>> {
        return db.favouritesDao().getIds()
    }

    override suspend fun getById(trackId: Int): VacancyFull? {
        return db.favouritesDao().getById(trackId)?.toVacancyFull()
    }

    override suspend fun upsertVacancy(vacancyFull: VacancyFull) {
        db.favouritesDao().upsert(vacancyFull.toVacancyEntity())
    }

    override suspend fun deleteVacancy(vacancyId: Int) {
        db.favouritesDao().delete(vacancyId)
    }

}
