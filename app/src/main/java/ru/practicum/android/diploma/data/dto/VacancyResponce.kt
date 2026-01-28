package ru.practicum.android.diploma.data.dto

data class VacancyResponse(val found: Int, val pages: Int, val page: Int, val vacancies: List<VacancyDto>) : Response()
