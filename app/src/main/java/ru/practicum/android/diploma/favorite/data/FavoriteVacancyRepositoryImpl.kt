package ru.practicum.android.diploma.favorite.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.database.AppDatabase
import ru.practicum.android.diploma.database.converters.VacancyDbConverter
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class FavoriteVacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConvert: VacancyDbConverter
) : FavoriteVacancyRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        appDatabase.favoriteVacancy().insertVacancy(vacancyDbConvert.map(vacancy))
    }

    override suspend fun deleteVacancyById(id: String) {
        appDatabase.favoriteVacancy().deleteVacancyById(id)
    }

    override fun getVacancies(): Flow<List<VacancySearch>> {
        return appDatabase.favoriteVacancy().getVacancies()
            .map { vacanciesList ->
                vacanciesList.map { vacancyEntity ->
                    vacancyDbConvert.convertToVacancySearch(vacancyEntity)
                }
            }
    }

    override fun getVacancyByID(id: String): Flow<Vacancy> {
        return appDatabase.favoriteVacancy().getVacancyByID(id)
            .map { vacancyEntity -> vacancyDbConvert.map(vacancyEntity) }
    }
}

