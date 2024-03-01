package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entyti.VacancyDetailEntity
import ru.practicum.android.diploma.data.dto.responseUnits.Employer
import ru.practicum.android.diploma.data.dto.responseUnits.LogoUrls
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyArea
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyType
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Contacts
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Employment
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Experience
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Phones
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.Schedule
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

object VacancyDetailDbConverter {

    fun VacancyDetailEntity.mapToVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contactName = contactName,
            contactEmail = contactEmail,
            contactPhone = contactPhone,
            contactComment = contactComment,
            employerName = employerName,
            employerUrl = employerUrl,
            salary = salary,
            schedule = schedule,
            employment = employment,
            experience = experience,
            keySkills = keySkills,
            description = description,
            isFavorite = true
        )
    }

    fun VacancyDetail.mapToVacancyDetailEntity(): VacancyDetailEntity {
        return VacancyDetailEntity(
            id = id,
            name = name,
            area = area,
            vacancyLink = vacancyLink,
            contactName = contactName,
            contactEmail = contactEmail,
            contactPhone = contactPhone,
            contactComment = contactComment,
            employerName = employerName,
            employerUrl = employerUrl,
            salary = salary,
            schedule = schedule,
            employment = employment,
            experience = experience,
            keySkills = keySkills,
            description = description,
            isFavorite = true
        )
    }

    fun List<VacancyDetailEntity>.mapToVacancyDetail(): List<VacancyDetail> =
        map { it.mapToVacancyDetail() }

    fun VacancyDetailDtoResponse.toVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = id,
            name = name,
            area = area.name,
            vacancyLink = vacancyLink,
            contactName = contacts?.name,
            contactEmail = contacts?.email,
            contactPhone = "buildPhoneNumbers(contacts?.phones)",
            contactComment = "buildPhoneComments(contacts?.phones)",
            employerName = employer?.name,
            employerUrl = employer?.logoUrls?.art90,
            salary = formatSalary(salary),
            schedule = schedule?.name,
            employment = employment?.name,
            experience = experience?.name,
            keySkills = "buildKeySkills(keySkills)",
            description = description
        )
    }

    fun buildPhoneNumbers(phones: Array<Phones>?): List<String?> {
        var phoneString: String
        val phoneList = mutableListOf<String>()
        phones?.forEach {
            phoneString = "+${it.country} (${it.city}) ${it.number}"
            phoneList.add(phoneString)
        }
        return phoneList
    }

    fun buildPhoneComments(phones: Array<Phones>?): List<String?> {
        val commentList = mutableListOf<String?>()
        phones?.forEach {
            commentList.add(it.comment)
        }
        return commentList
    }

    fun buildKeySkills(keySkills: List<KeySkillVacancyDetail>): List<String> {
        val skillsList = mutableListOf<String>()
        keySkills.forEach {
            skillsList.add(it.name)
        }
        return skillsList
    }

    private fun formatSalary(salary: Salary?): String {
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
            else -> ""
        }

        val stringBuilder = StringBuilder()

        salary.from?.let {
            stringBuilder.append("от ${salary.from} ")
        }

        salary.to?.let {
            stringBuilder.append("до ${salary.to} ")
        }
        stringBuilder.append("$currency")

        return stringBuilder.toString()
    }
}
