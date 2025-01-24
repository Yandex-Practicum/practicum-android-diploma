package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDto

interface NetworkClient {
    suspend fun doRequestVacancies(): VacanciesResponseDto?
    suspend fun doRequestVacancy(id: String): VacancyDto?
}
