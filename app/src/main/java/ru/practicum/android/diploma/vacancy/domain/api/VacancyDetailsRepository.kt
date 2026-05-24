package ru.practicum.android.diploma.vacancy.domain.api

import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

interface VacancyDetailsRepository {
    suspend fun getById(id: String): Resource<VacancyDetails>
}
