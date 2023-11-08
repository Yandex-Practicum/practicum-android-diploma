package ru.practicum.android.diploma.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.favorite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import java.lang.reflect.Type

class FavouriteRepositoryImpl(private val dao: VacancyDao, private val gson: Gson) :
    FavouriteRepository {
    override suspend fun addToFavourite(fullVacancy: FullVacancy) {
        val vacancyEntity = toVacancyEntity(fullVacancy)
        dao.addVacancy(vacancyEntity)
    }

    override suspend fun deleteFromFavourite(fullVacancy: FullVacancy) {
        val vacancyEntity = toVacancyEntity(fullVacancy)
        dao.delete(vacancyEntity)
    }
    override fun getFavouriteStatus(vacancyId: String): Flow<Boolean> = flow {
        val listId = dao.getVacanciesId()
        emit(listId.contains(vacancyId))
    }

    override fun getFavouriteVacancies(): Flow<List<Vacancy>> = flow {
        val favouriteVacancies = dao.getFavoriteVacancies()
        emit(fromVacancyEntity(favouriteVacancies))
    }

    private fun toVacancyEntity(fullVacancy: FullVacancy): VacancyEntity {
        return VacancyEntity(
            fullVacancy.id,
            fullVacancy.name,
            fullVacancy.city,
            fullVacancy.employerName,
            fullVacancy.employerLogoUrl ?: "",
            gson.toJson(fullVacancy.salary),
        )
    }

    private fun fromVacancyEntity(vacancies: List<VacancyEntity>): List<Vacancy> {
        return vacancies.map { vacancy -> mapFromEntity(vacancy) }
    }

    private fun mapFromEntity(vacancyEntity: VacancyEntity): Vacancy {
        return Vacancy(
            vacancyEntity.id,
            vacancyEntity.name,
            vacancyEntity.city,
            vacancyEntity.employerName,
            0,
            vacancyEntity.employerLogoUrl,
            convertToSalary(vacancyEntity.salary),
        )
    }

    private fun convertToSalary(json: String?): Salary? {
        val myClassObject: Type = object : TypeToken<Salary>() {}.type
        return if (!json.isNullOrEmpty()) gson.fromJson(json, myClassObject) else null
    }
}
