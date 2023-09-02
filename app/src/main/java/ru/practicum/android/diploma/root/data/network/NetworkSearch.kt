package ru.practicum.android.diploma.root.data.network

import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsDto
import ru.practicum.android.diploma.features.vacancydetails.data.models.VacancyDetailsRequest
import ru.practicum.android.diploma.root.data.network.models.Response

interface NetworkSearch {

    suspend fun getVacancyById(dto: VacancyDetailsRequest): Response<VacancyDetailsDto>

}