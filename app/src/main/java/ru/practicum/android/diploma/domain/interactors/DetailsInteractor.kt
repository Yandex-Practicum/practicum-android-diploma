package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface DetailsInteractor {
    suspend fun getVacancyDetails(id: String): Resource<VacancyDetail>
}
