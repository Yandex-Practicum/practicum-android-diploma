package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.vacancy.models.VacancyDetails

class VacancyDetailsMapper(private val htmlParser: HtmlParser) {
    fun VacancyDetails.toVO(): VacancyDetailsVO = VacancyDetailsVO(
        title = this.title,
        salary = buildSalaryString(this.salaryFrom, this.salaryTo, this.salaryCurrency ?: ""),
        experience = this.experience,
        employment = this.employment,
        schedule = this.schedule,
        description = htmlParser.fromHtml(this.descriptionHtml ?: ""),
        addressOrRegion = this.address?.ifBlank { null } ?: this.areaName.orEmpty()
    )

    private fun buildSalaryString(salaryFrom: Int?, salaryTo: Int?, currency: String): String {
        return buildList {
            if (salaryFrom != null) add("от $salaryFrom")
            if (salaryTo != null) add("до $salaryTo")
        }.joinToString(" ") + if ((salaryFrom != null || salaryTo != null) && currency.isNotEmpty()) " $currency" else ""
    }
}
