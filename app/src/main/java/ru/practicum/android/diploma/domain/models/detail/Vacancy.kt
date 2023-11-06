package ru.practicum.android.diploma.domain.models.detail

import ru.practicum.android.diploma.data.dto.Contacts
import ru.practicum.android.diploma.domain.models.mok.EmployerModel
import ru.practicum.android.diploma.domain.models.mok.Salary

data class Vacancy(
    val name: String?,
    val address: String?,
    val brandedDescription: String?,
    val contacts: Contacts,
    val description: String?,
    val employer: EmployerModel?,
    val experience: String?,
    val salary: Salary?,
    val skills: CharSequence?,
    val requirements: CharSequence?
)
