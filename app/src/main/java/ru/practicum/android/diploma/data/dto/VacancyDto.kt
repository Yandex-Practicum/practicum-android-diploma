package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.elementsVacancyDto.ElementDto
import ru.practicum.android.diploma.data.dto.elementsVacancyDto.EmployerDto
import ru.practicum.android.diploma.data.dto.elementsVacancyDto.SalaryDto
import ru.practicum.android.diploma.data.dto.elementsVacancyDto.SnippetDto

data class VacancyDto(
    val name: String,
    val salary: SalaryDto?,
    val area: ElementDto?,
    val employer: EmployerDto?,
    val experience: ElementDto?,
    val snippet: SnippetDto, //!requirement!, !responsibility!
    val schedule: ElementDto, //id, !name!
    val employment: ElementDto,

    @SerializedName("professional_roles")
    val professionalRoles: List<ElementDto>?,

    )
