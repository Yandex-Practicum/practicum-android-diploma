package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbInteractorImpl : VacancyDbInteractor {
<<<<<<< feat_45
    override suspend fun addVacancyToFavorites(vacancy: VacancyDetail) {}

    override suspend fun deleteVacancyFromFavorites(vacancyId: String) {}

    override suspend fun checkVacancyIsFavorite(vacancyId: String): Boolean {
=======
    override fun addVacancyToFavorites(vacancy: VacancyDetail) {}

    override fun deleteVacancyFromFavorites(vacancyId: String) {}

    override fun checkVacancyIsFavorite(vacancyId: String): Boolean {
>>>>>>> develop
        return false
    }
}
