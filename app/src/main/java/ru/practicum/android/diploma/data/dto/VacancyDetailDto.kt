package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.models.InfoClass

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val area: InfoClass,
    val employer: EmployerDto,
    val salary_range: SalaryRangeDto,
    val key_skills: List<InfoClass>,
    val employment_form: InfoClass?,
    val description: String,
    val professional_roles: List<String>,
    val experience: InfoClass
) : Response()
