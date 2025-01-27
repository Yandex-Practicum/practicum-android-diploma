package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    suspend fun doRequestVacancies(text: String?): Response
    suspend fun doRequestVacancyDetails(vacancyId: String): Response
}
