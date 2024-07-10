package ru.practicum.android.diploma.vacancydetails.domain.models

class DetailsResult(
    val page: Int,
    val perPage: Int,
    val pages: Int,
    val found: Int,
    val vacancies: List<DetailsResultItem>,
)
