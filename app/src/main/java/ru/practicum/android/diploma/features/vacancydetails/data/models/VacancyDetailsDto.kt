package ru.practicum.android.diploma.features.vacancydetails.data.models

import com.google.gson.annotations.SerializedName

data class VacancyDetailsDto(

    @SerializedName("id") val vacancyId: String,
    @SerializedName("name") val vacancyName: String,
    @SerializedName("salary") val salary: Salary?,
    @SerializedName("employer") val employer: Employer?,
    @SerializedName("area") val vacancyArea: VacancyArea,
    @SerializedName("experience") val experienceDescription: Experience?,
    @SerializedName("employment") val employmentType: EmploymentType?,
    @SerializedName("schedule") val scheduleType: ScheduleType?,
    @SerializedName("description") val vacancyDesc: String,
    @SerializedName("branded_description") val vacancyBrandedDesc: String?,
    @SerializedName("key_skills") val keySkills: List<String>,
    @SerializedName("contacts") val contacts: Contacts?,
    @SerializedName("response_url") val responseUrl: String?
) {

    data class Contacts(
        @SerializedName("email") val contactsEmail: String?,
        @SerializedName("name") val contactsName: String?,
        @SerializedName("phones") val phones: List<Phone>?,
    )

    data class Salary(
        @SerializedName("currency") val currency: String?,
        @SerializedName("from") val from: Int?,
        @SerializedName("gross") val isGross: Boolean?,
        @SerializedName("to") val to: Int?
    )

    data class Employer(
        @SerializedName("logo_urls") val logoUrls: LogoUrls?,
        @SerializedName("name") val employerName: String,

        ) {
        data class LogoUrls(
            @SerializedName("90") val logoUrl90: String,
        )
    }

    data class EmploymentType(
        @SerializedName("name") val employmentTypeName: String,
    )

    data class Experience(
        @SerializedName("name") val experience: String,
    )

    data class Phone(
        @SerializedName("formatted") val phoneNumber: String,
    )

    data class ScheduleType(
        @SerializedName("name") val scheduleTypeName: String,
    )

    data class VacancyArea(
        @SerializedName("name") val area: String,
    )

}
