package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.VacancyDao
import ru.practicum.android.diploma.domain.favorite.FavouriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

class FavouriteRepositoryImpl(private val dao: VacancyDao, private val mapper: VacancyEntityMapper,) :
    FavouriteRepository {
    override suspend fun addToFavourite(fullVacancy: FullVacancy) {
        val vacancyEntity = mapper.toVacancyEntity(fullVacancy)
        dao.addVacancy(vacancyEntity)
    }

    override suspend fun deleteFromFavourite(fullVacancy: FullVacancy) {
        val vacancyEntity = mapper.toVacancyEntity(fullVacancy)
        dao.delete(vacancyEntity)
    }
    override fun getFavouriteStatus(vacancyId: String): Flow<Boolean> = flow {
        val listId = dao.getVacanciesId()
        emit(listId.contains(vacancyId))
    }

    override fun getFavouriteVacancies(): Flow<List<Vacancy>> = flow {
        val favouriteVacancies = dao.getFavoriteVacancies()
        emit(mapper.fromVacancyEntityToList(favouriteVacancies))
    }

    override suspend fun getFullVacancy(id:String): FullVacancy? {
        val favouriteVacancy = dao.getFavoriteVacancy(id)
        return if(favouriteVacancy != null) mapper.fromVacancyEntityToFullVacancy(favouriteVacancy) else null
    }

    override suspend fun updateVacancy(vacancy: FullVacancy?) {
        if (vacancy != null) {
            val listId = dao.getVacanciesId()
            if(listId.contains(vacancy.id)) {
                val vacancyEntity = mapper.toVacancyEntity(vacancy)
                dao.updateVacancy(vacancyEntity)
            }
        }
    }

}
