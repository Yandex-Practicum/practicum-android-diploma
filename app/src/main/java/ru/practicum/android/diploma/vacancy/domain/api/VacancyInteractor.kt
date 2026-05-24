package ru.practicum.android.diploma.vacancy.domain.api

import ru.practicum.android.diploma.core.data.network.Resource
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

interface VacancyInteractor {
    suspend fun getById(id: String): Resource<VacancyDetails>
}
