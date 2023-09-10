package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.dto.general_models.Area
import ru.practicum.android.diploma.search.data.network.dto.general_models.Contacts
import ru.practicum.android.diploma.search.data.network.dto.general_models.Department
import ru.practicum.android.diploma.search.data.network.dto.general_models.Employer
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary
import ru.practicum.android.diploma.search.data.network.dto.general_models.Schedule

@Serializable
data class VacancyDto(
    @SerialName("id") val id: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("area") val area: Area?,
    @SerialName("contacts") val contacts: Contacts? = Contacts(),
    @SerialName("department") val department: Department? = Department(),
    @SerialName("employer") val employer: Employer? = Employer(),
    @SerialName("published_at") val publishedAt: String? = "",
    @SerialName("salary") val salary: Salary? = Salary(),
    @SerialName("schedule") val schedule: Schedule? = Schedule(),
)