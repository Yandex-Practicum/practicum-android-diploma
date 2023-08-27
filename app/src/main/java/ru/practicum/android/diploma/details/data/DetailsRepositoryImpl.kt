package ru.practicum.android.diploma.details.data

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.search.domain.Vacancy
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(): DetailsRepository {
    override fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        TODO("Not yet implemented")
    }

    override fun removeVacancyFromFavorite(id: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun addVacancyToFavorite(vacancy: Vacancy) {
        TODO("Not yet implemented")
    }
}