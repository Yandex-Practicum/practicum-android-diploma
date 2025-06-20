package ru.practicum.android.diploma.ui.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsMapper(private val htmlParser: HtmlParser) {
    fun VacancyDetails.toVO(): VacancyDetailsVO = VacancyDetailsVO(
        title = this.vacancy.name,
        salary = buildSalaryString(this.vacancy.salaryFrom, this.vacancy.salaryTo, this.vacancy.salaryCurr),
        experience = this.experience,
        employment = this.employment,
        schedule = this.schedule.toString(),
        description = htmlParser.fromHtml(this.description),
        addressOrRegion = this.address.ifBlank { null } ?: this.vacancy.areaName,
        isFavorite = this.isFavorite,
        keySkills = this.keySkills,
        logoUrl = this.vacancy.employerUrls,
        employerName = this.vacancy.employerName,
        url = this.url
    )

    private fun buildSalaryString(salaryFrom: Int?, salaryTo: Int?, currency: String): String {
        val currencySymbol = when (currency.uppercase()) {
            "RUB", "RUR" -> "₽"
            "BYR" -> "Br"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "UZS" -> "сум"
            "GEL" -> "₾"
            "KGT" -> "сом"
            else -> currency
        }
        return buildList {
            if (salaryFrom != null) add("от $salaryFrom")
            if (salaryTo != null) add("до $salaryTo")
        }.joinToString(" ") +
            if ((salaryFrom != null || salaryTo != null) && currency.isNotEmpty()) " $currencySymbol" else ""
    }
}
