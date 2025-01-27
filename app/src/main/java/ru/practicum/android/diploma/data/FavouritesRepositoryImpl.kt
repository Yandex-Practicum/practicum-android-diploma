package ru.practicum.android.diploma.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.FavouritesRepository
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy


class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase, private val gson: Gson
) : FavouritesRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        val vacancyEntity = vacancy.toEntity()
        appDatabase.getFavoriteVacancyDao().saveVacancy(vacancyEntity)
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        val vacancyEntity = vacancy.toEntity()
        appDatabase.getFavoriteVacancyDao().deleteVacancy(vacancyEntity)
    }

    override fun getVacancies(): Flow<List<Vacancy>> {
        return appDatabase.getFavoriteVacancyDao().getVacancies()
            .map { vacancyEntity -> vacancyEntity.map { it.toDomain() } }
    }

    private fun FavoriteVacancyEntity.toDomain(): Vacancy {
        return Vacancy(
            id = this.id,
            name = this.name,
            logoUrl90 = this.logoUrl,
            area = this.area,
            salary = this.salary?.let { gson.fromJson(it, object : TypeToken<Salary>() {}.type) },
            employerName = this.employerName,
            description = this.description,
            alternateUrl = this.alternateUrl,
            employment = this.employment,
            experience = this.experience

        )
    }

    private fun Vacancy.toEntity(): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = this.id,
            name = this.name,
            logoUrl = this.logoUrl90,
            area = this.area,
            salary = this.salary?.let { gson.toJson(it) },
            employerName = this.employerName,
            description = this.description,
            alternateUrl = this.alternateUrl,
            employment = this.employment,
            experience = this.experience
        )
    }
}
