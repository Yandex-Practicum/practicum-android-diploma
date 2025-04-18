package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.data.dto.additional.ScheduleDto

@Serializable
data class VacancyShortDto(
    @SerialName("published_at") val postedAt: String? = null,
    @SerialName("id") val vacancyId: String? = null,
    val name: String? = null,
    val area: AreaDto? = null,
    val employer: EmployerDto? = null,
    val salary: SalaryDto? = null,
    val schedule: ScheduleDto? = null,
    val industries: List<IndustryDto> = emptyList()
)
