package ru.practicum.android.diploma.search.data.network.converter

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.data.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.details.data.dto.assistants.KeySkillDto
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto
import ru.practicum.android.diploma.search.data.network.dto.general_models.Phone
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class VacancyModelConverter @Inject constructor(
    private val context: Context,
) {

    fun mapList(list: List<VacancyDto>): List<Vacancy> {
        return list.map { it.map() }
    }

    private fun VacancyDto.map(): Vacancy {
        return with(this) {
            Vacancy(
                id = id ?: "",
                iconUri = employer?.logo_urls?.url240 ?: "",
                title = name ?: "",
                company = employer?.name ?: "",
                salary = createSalary(salary) ?: context.getString(R.string.empty_salary),
                area = area?.name ?: "",
                date = published_at ?: "",
            )
        }
    }

    private fun createSalary(salary: Salary?): String? {
        if (salary == null) return null
        val result = StringBuilder()
        if (salary.from != null) {
            result.append(context.getString(R.string.from))
            result.append(" ")
            result.append(salary.from)
            result.append(" ")
        }
        if (salary.to != null) {
            result.append(context.getString(R.string.to))
            result.append(" ")
            result.append(salary.to)
        }
        when (salary.currency) {
            "AZN" -> result.append(" ₼")
            "BYR" -> result.append(" Br")
            "EUR" -> result.append(" €")
            "GEL" -> result.append(" ₾")
            "KGS" -> result.append(" сом")
            "KZT" -> result.append(" ₸")
            "RUR" -> result.append(" ₽")
            "UAH" -> result.append(" ₴")
            "USD" -> result.append(" $")
            "UZS" -> result.append(" so'm")
            else -> result.append(salary.currency)
        }
        return result.toString()
    }

    fun mapDetails(details: VacancyFullInfoModelDto): VacancyFullInfo {
        return details.mapToDetails()
    }

    private fun VacancyFullInfoModelDto.mapToDetails(): VacancyFullInfo {
        return with(this) {
            VacancyFullInfo(
                id = id,
                description = description ?: "",
                experience = experience?.name ?: "",
                employment = employment?.name ?: "",
                schedule = schedule?.name ?: "",
                area = area?.name ?: "",
                salary = createSalary(salary) ?: context.getString(R.string.empty_salary),
                company = employer?.name ?: "",
                logo = employer?.logo_urls?.url240 ?: "",
                title = name ?: "",
                contactEmail = contacts?.email ?: context.getString(R.string.no_info),
                contactName = contacts?.name ?: context.getString(R.string.no_info),
                keySkills = keySkillsToString(key_skills),
                contactPhones = createPhones(contacts?.phones),
                alternateUrl = alternate_url ?: "",
            )
        }
    }

    private fun keySkillsToString(keySkills: List<KeySkillDto>?): String {
        if (keySkills != null) {
            return keySkills.map { "• ${it.name}" }
                .joinToString("\n")
        }
        return ""
    }

    private fun createPhones(phones: List<Phone?>?): List<String> {
        if (phones == null) return emptyList()
        val phoneList = mutableListOf<String>()
        repeat(phones.size) { pho ->
            val number: String = "+" + phones[pho]?.country +
                    " (${phones[pho]?.city}) " + phones[pho]?.number
            phoneList.add(pho, number)
        }
        return phoneList
    }

}