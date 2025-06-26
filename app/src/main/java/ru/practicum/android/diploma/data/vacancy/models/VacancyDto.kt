package ru.practicum.android.diploma.data.vacancy.models

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    val id: String,
    val name: String,
    val area: VacancyAreaDto,
    @SerializedName("salary_range") val salaryRange: VacancySalaryRangeDto?,
    val employer: VacancyEmployerDto,
) {
    data class VacancyAreaDto(
        val id: String,
        val name: String,
        val url: String,
    )

    data class VacancySalaryRangeDto(
        val currency: String,
        val frequency: VacancySalaryRangeFrequencyDto?,
        val from: Int?,
        val to: Int?,
        val gross: Boolean,
    ) {
        data class VacancySalaryRangeFrequencyDto(
            val id: String,
            val name: String,
        )
    }

    data class VacancyEmployerDto(
        @SerializedName("accredited_it_employer") val accreditedItEmployer: Boolean,
        @SerializedName("alternate_url") val alternateUrl: String?,
        val id: String,
        @SerializedName("logo_urls") val logoUrls: VacancyEmployerLogoUrlDto?,
        val name: String,
        val trusted: Boolean,
        val url: String?,
        @SerializedName("vacancies_url") val vacanciesUrl: String?,
    ) {
        data class VacancyEmployerLogoUrlDto(
            @SerializedName("90") val size90: String,
            @SerializedName("240") val size240: String,
            val original: String,
        )
    }
}
