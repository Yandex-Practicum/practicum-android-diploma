package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.models.InfoClass

data class VacanyItemDto(
    val id: String,
    val name: String,
    val area: InfoClass,
    val employer: EmployerDto,
    @SerializedName("salary_range") val salaryRange: SalaryRangeDto?,
)
