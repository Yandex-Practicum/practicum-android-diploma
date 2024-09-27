package ru.practicum.android.diploma.networkClient.domain.api

interface VacanciesRepository {
    fun searchVacancies(options: Map<String, String>)
    fun listVacancy(id: String)
    fun listAreas()
    fun listIndustries()
}
