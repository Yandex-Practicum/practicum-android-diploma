package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.IFavVacanciesInteractor
import ru.practicum.android.diploma.domain.api.IFavVacanciesRepository

class IFavVacanciesInteractorImpl(private val iFavVacanciesRepository: IFavVacanciesRepository) :
    IFavVacanciesInteractor {
    private var isChecked = false

    override fun getFavorite(): Flow<List<Vacancy>> {
        return iFavVacanciesRepository.getAll()
    }

    override fun addToFavorite(vacancy: Vacancy) {
        iFavVacanciesRepository.add(vacancy)
    }

    override fun deleteFromFavorite(vacancy: Vacancy) {
        iFavVacanciesRepository.delete(vacancy)
    }

    override suspend fun isChecked(vacancy: Vacancy): Boolean {
        getFavorite().collect { vacancyList ->
            if (vacancyList.contains(vacancy.id))
                isChecked = true
        }
        return isChecked
    }
}
