package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDetailsMapper(private val htmlParser: HtmlParser) {
    fun VacancyDetail.toVO(): VacancyDetailsVO = VacancyDetailsVO(
        title = this.name,
        salary = buildSalaryString(this.salaryFrom, this.salaryTo, this.salaryCurr),
        experience = this.experience,
        employment = this.employerName,
        schedule = this.schedule.toString(),
        description = htmlParser.fromHtml(this.description),
        addressOrRegion = this.address.ifBlank { null } ?: this.areaName,
        isFavorite = this.isFavorite,
        keySkills = this.keySkills,
        logoUrl = this.employerUrls,
        employerName = this.employerName
    )

    private fun buildSalaryString(salaryFrom: Int?, salaryTo: Int?, currency: String): String {
        return buildList {
            if (salaryFrom != null) add("от $salaryFrom")
            if (salaryTo != null) add("до $salaryTo")
        }.joinToString(" ") +
            if ((salaryFrom != null || salaryTo != null) && currency.isNotEmpty()) " $currency" else ""
    }
}




