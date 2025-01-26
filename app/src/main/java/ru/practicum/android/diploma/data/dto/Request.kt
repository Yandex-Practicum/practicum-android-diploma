package ru.practicum.android.diploma.data.dto

sealed class Request {
    class VacanciesSearchRequest : Request()
    class VacancyDetailsRequest(val vacancyId: String) : Request()
}
