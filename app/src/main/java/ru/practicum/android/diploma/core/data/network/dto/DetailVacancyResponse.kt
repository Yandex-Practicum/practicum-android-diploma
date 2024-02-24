package ru.practicum.android.diploma.core.data.network.dto

import com.google.gson.annotations.SerializedName

data class DetailVacancyResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("employer")
    val employerInfo: EmployerDto?,
    @SerializedName("contacts")
    val contactInfo: ContactInfoDto?,
    @SerializedName("experience")
    val experience: ExperienceDto?,
    @SerializedName("description")
    val description: String,
    @SerializedName("key_skills")
    val keySkills: List<String>,
    @SerializedName("salary")
    val salaryInfo: SalaryDto?,
    @SerializedName("schedule")
    val workScheduleInfo: WorkScheduleDto?,
    @SerializedName("employment")
    val employment: EmploymentDto?,
    @SerializedName("address")
    val locationInfo: AddressDto?,
    @SerializedName("alternate_url")
    val alternateUrl: String
) : Response()
