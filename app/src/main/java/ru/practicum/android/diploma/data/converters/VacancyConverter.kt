package ru.practicum.android.diploma.data.converters

import android.icu.text.DecimalFormat
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Address
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.detail.VacancyDetail

object VacancyConverter {
    fun VacancyDto.toVacancy(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            area = area.name,
            employer = employer.name,
            salary = formatSalary(salary),
            employerImgUrl = employer.logoUrls?.art240
        )
    }

    fun VacancyDetailDtoResponse.toVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area.name,
            vacancyLink = vacancyLink,
            contactName = contacts?.name,
            contactEmail = contacts?.email,
            contactPhones = buildPhoneNumbers(contacts?.phones),
            contactComments = buildPhoneComments(contacts?.phones),
            employerName = employer?.name,
            employerUrl = employer?.logoUrls?.original,
            salary = formatSalary(salary),
            schedule = schedule?.name,
            employment = employment?.name,
            experience = experience?.name,
            keySkills = buildKeySkills(keySkills),
            address = createAddress(address),
            description = description
        )
    }

    private fun createAddress(address: Address?): String? {
        return if (address != null) {
            val city = if (address.city != null) "${address.city}" else ""
            val street = if (address.street != null) ", ${address.street}" else ""
            val building = if (address.building != null) ", ${address.building}" else ""
            "$city$street$building"
        } else { null }
    }

    fun formatSalary(salary: Salary?): String {
        if (salary == null) return "Зарплата не указана"

        val currency = when (salary.currency) {
            "RUR" -> "₽"
            "EUR" -> "€"
            "KZT" -> "₸"
            "AZN" -> "\u20BC"
            "USD" -> "$"
            "BYR" -> "\u0042\u0072"
            "GEL" -> "\u20BE"
            "UAH" -> "\u20b4"
            "UZS" -> "Soʻm"
            else -> ""
        }

        val stringBuilder = StringBuilder()

        salary.from?.let {
            stringBuilder.append("от ${formatSalary(salary.from)} ")
        }

        salary.to?.let {
            stringBuilder.append("до ${formatSalary(salary.to)} ")
        }
        stringBuilder.append("$currency")

        return stringBuilder.toString()
    }

    private fun formatSalary(salary: Int): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = 3

        return df.format(salary)
    }

    fun buildPhoneNumbers(phones: List<Phones>?): List<String?> {
        var phoneString: String
        val phoneList = mutableListOf<String>()
        phones?.forEach {
            phoneString = "+${it.country}" +
                " (${it.city}) " +
                "${it.number.substring(0, 3)}-" +
                "${it.number.substring(3, 5)}-" +
                it.number.substring(5, 7)
            phoneList.add(phoneString)
        }
        return phoneList
    }

    fun buildPhoneComments(phones: List<Phones>?): List<String?> {
        val commentList = mutableListOf<String?>()
        phones?.forEach {
            commentList.add(it.comment)
        }
        return commentList
    }

    private fun buildKeySkills(keySkills: List<KeySkillVacancyDetail>): List<String> {
        val skillsList = mutableListOf<String>()
        keySkills.forEach {
            skillsList.add(it.name)
        }
        return skillsList
    }
}
