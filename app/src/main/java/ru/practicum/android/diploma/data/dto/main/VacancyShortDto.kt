package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.data.dto.additional.ScheduleDto

@Serializable
data class VacancyShortDto(
    @SerialName("published_at") val postedAt: String,
    @SerialName("id") val vacancyId: String,
    val name: String,
    val area: AreaDto,
    val employer: EmployerDto,
    val salary: SalaryDto? = null,
    val schedule: ScheduleDto? = null,
    val industries: List<IndustryDto> = emptyList()
)
