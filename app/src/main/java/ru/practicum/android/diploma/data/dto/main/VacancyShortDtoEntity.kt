package ru.practicum.android.diploma.data.dto.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacancyShortDtoEntity(
    @SerialName("published_at") val postedAt: String,
    @SerialName("id") val vacancyId: String,
    val name: String,
    val area: AreaDto,
    val employer: EmployerDto,
    val salary: SalaryDto? = null,
    val industries: List<IndustryDto> = emptyList()
)
