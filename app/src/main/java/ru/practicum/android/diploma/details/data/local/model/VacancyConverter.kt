package ru.practicum.android.diploma.details.data.local.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class VacancyConverter @Inject constructor() {

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
}