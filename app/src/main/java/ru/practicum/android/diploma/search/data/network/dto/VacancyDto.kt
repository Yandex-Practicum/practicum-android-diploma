package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.general_models.Area
import ru.practicum.android.diploma.search.data.network.dto.general_models.Contacts
import ru.practicum.android.diploma.search.data.network.dto.general_models.Department
import ru.practicum.android.diploma.search.data.network.dto.general_models.Employer
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary
import ru.practicum.android.diploma.search.data.network.dto.general_models.Schedule

@Serializable
data class VacancyDto(
    val id: String? = "",
    val name: String? = "",
    val area: Area?,
    val contacts: Contacts? = Contacts(),
    val department: Department? = Department(),
    val employer: Employer? = Employer(),
    val published_at: String? = "",
    val salary: Salary? = Salary(),
    val schedule: Schedule? = Schedule(),
)