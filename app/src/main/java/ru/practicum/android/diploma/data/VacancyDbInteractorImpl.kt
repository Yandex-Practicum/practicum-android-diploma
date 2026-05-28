package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbInteractorImpl : VacancyDbInteractor {
    override fun addVacancyToFavorites(vacancy: VacancyDetail) {}

    override fun deleteVacancyFromFavorites(vacancyId: String) {}

    override fun checkVacancyIsFavorite(vacancyId: String): Boolean {
        return false
    }
}
