package ru.practicum.android.diploma.features.vacancydetails.domain

import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails
import ru.practicum.android.diploma.root.data.Outcome

interface VacancyDetailsInteractor {

    suspend fun getVacancyById(id: String): Outcome<VacancyDetails>
}