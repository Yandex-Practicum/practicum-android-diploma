package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsMapper(private val htmlParser: HtmlParser) {
    fun VacancyDetails.toVO(): VacancyDetailsVO = VacancyDetailsVO(
        title = this.name,
        salary = buildSalaryString(this.salaryFrom, this.salaryTo, this.salaryCurr),
        experience = this.experience,
        employment = this.employment,
        schedule = this.schedule.toString(),
        description = htmlParser.fromHtml(this.description),
        addressOrRegion = this.address.ifBlank { null } ?: this.areaName,
        isFavorite = this.isFavorite,
        keySkills = this.keySkills,
        logoUrl = this.employerUrls,
        employerName = this.employerName,
        url = this.url
    )

    private fun buildSalaryString(salaryFrom: Int?, salaryTo: Int?, currency: String): String {
        return buildList {
            if (salaryFrom != null) add("от $salaryFrom")
            if (salaryTo != null) add("до $salaryTo")
        }.joinToString(" ") +
            if ((salaryFrom != null || salaryTo != null) && currency.isNotEmpty()) " $currency" else ""
    }
}
