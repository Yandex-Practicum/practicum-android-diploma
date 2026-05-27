package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface DetailsRepository {
    suspend fun getVacancyDetails(id: String): Resource<VacancyDetail>
}
