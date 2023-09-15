package ru.practicum.android.diploma.search.data.network.converter

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.data.local.model.VacancyFullInfoEntity
import ru.practicum.android.diploma.details.data.network.dto.VacancyFullInfoModelDto
import ru.practicum.android.diploma.details.data.network.dto.assistants.KeySkillDto
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto
import ru.practicum.android.diploma.search.data.network.dto.general_models.Phone
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary
import ru.practicum.android.diploma.search.data.network.dto.response.VacanciesResponse
import ru.practicum.android.diploma.search.domain.models.Vacancies
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
                date = publishedAt ?: "",
            )
        }
    }

    @NewResponse
    fun vacanciesResponseToVacancies(vacanciesResponse: VacanciesResponse) : Vacancies{
        return with(vacanciesResponse) {
            Vacancies(
                found = found,
                items = mapList(items),
                page = page,
                pages = pages,
                per_page = per_page,
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
                contactEmail = contacts?.email ?: "",
                contactName = contacts?.name ?: "",
                keySkills = keySkillsToString(keySkills),
                contactPhones = createPhones(contacts?.phones),
                contactComment = contacts?.phones?.getOrNull(0)?.comment ?: "",
                alternateUrl = alternateUrl ?: "",
            )
        }
    }


    fun toFullInfoEntity(vacancy: VacancyFullInfo): VacancyFullInfoEntity {
        return with(vacancy) {
            VacancyFullInfoEntity(
                id = id,
                experience = experience,
                employment = employment,
                schedule = schedule,
                description = description,
                keySkills = keySkills,
                area = area,
                salary = salary,
                date = date,
                company = company,
                logo = logo,
                title = title,
                contactEmail = contactEmail,
                contactName = contactName,
                contactComment = contactComment,
                contactPhones = Json.encodeToString(contactPhones),
                alternateUrl = alternateUrl
            )
        }
    }

    fun mapToVacancies(entities: List<VacancyFullInfoEntity>): List<Vacancy>{
        return entities.map { toVacancy(it) }
    }

    private fun toVacancy(vacancyEntity: VacancyFullInfoEntity): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id,
                iconUri = logo,
                title = title,
                company = company,
                salary = salary,
                area = area,
                date = date,
            )
        }
    }

    fun entityToModel(vacancyEntity: VacancyFullInfoEntity): VacancyFullInfo {
        return with(vacancyEntity) {
            VacancyFullInfo(
                id = id,
                experience = experience,
                employment = employment,
                schedule = schedule,
                description = description,
                keySkills = keySkills,
                area = area,
                salary = salary,
                date = date,
                company = company,
                logo = logo,
                title = title,
                contactEmail = contactEmail,
                contactName = contactName,
                contactComment = contactComment,
                contactPhones = Json.decodeFromString(contactPhones),
                alternateUrl = alternateUrl,
                isInFavorite = true
            )
        }
    }

    private fun keySkillsToString(keySkills:List<KeySkillDto>?): String {
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