package ru.practicum.android.diploma.data.vacancy.models

data class VacancyDto(
    val id: String,
    val name: String,
    val area: VacancyAreaDto,
    val salary_range: VacancySalaryRangeDto,
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
        val accredited_it_employer: Boolean,
        val alternate_url: String?,
        val id: String,
        val logo_urls: VacancyEmployerLogoUrlDto?,
        val name: String,
        val trusted: Boolean,
        val url: String?,
        val vacancies_url: String?,

    ) {
        data class VacancyEmployerLogoUrlDto(
            val `90`: String,
            val `240`: String,
            val original: String,
        )
    }
}
