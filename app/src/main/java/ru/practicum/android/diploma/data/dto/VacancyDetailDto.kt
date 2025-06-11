package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.models.InfoClass

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val area: InfoClass,
    val employer: EmployerDto,
    @SerializedName("salary_range") val salaryRange: SalaryRangeDto,
    @SerializedName("key_skills") val keySkills: List<InfoClass>,
    @SerializedName("employment_form") val employmentForm: InfoClass?,
    val description: String,
    @SerializedName("professional_roles") val professionalRoles: List<String>,
    val experience: InfoClass
) : Response()
