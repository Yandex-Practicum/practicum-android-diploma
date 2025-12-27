package ru.practicum.android.diploma.core.presentation.ui.model

data class VacancyListItemUi(
    val id: String,
    val logoUrl: String?,
    val vacancyName: String,
    val city: String?,
    val employer: String?,
    val salary: String?,
)
