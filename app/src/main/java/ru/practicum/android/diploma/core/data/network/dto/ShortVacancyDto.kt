package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

data class ShortVacancyDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("employer")
    val employerInfo: EmployerDto,
    @SerializedName("name")
    val name: String,
    @SerializedName("salary")
    val salaryInfo: SalaryDto?,
    @SerializedName("address")
    val locationInfo: AddressDto?
)
