package ru.practicum.android.diploma.root.domain

import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails
import ru.practicum.android.diploma.root.data.Outcome

interface VacancyRepository {
    suspend fun getVacancyById(id: String): Outcome<VacancyDetails>
}