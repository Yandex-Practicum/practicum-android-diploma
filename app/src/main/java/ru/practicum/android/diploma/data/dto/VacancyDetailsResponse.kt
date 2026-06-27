package ru.practicum.android.diploma.data.dto

/**
 * Оболочка для ответа Retrofit при запросе деталей вакансии.
 * Наследуется от Response, чтобы передавать resultCode.
 */
data class VacancyDetailsResponse(
    val vacancy: VacancyDto
) : Response()
