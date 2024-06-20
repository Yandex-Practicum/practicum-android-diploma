package ru.practicum.android.diploma.data.db

import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class VacancyConverter {

    fun map(domainVacancy: DomainVacancy): FavoriteVacancyEntity {
        val currentTimestamp: Long = java.util.Date().time

        return FavoriteVacancyEntity(
            vacancyId = domainVacancy.vacancyId,
            name = domainVacancy.name,
            city = domainVacancy.city,
            area = domainVacancy.area,
            salaryFrom = domainVacancy.salaryFrom,
            salaryTo = domainVacancy.salaryTo,
            salaryCurrency = domainVacancy.salaryCurrency,
            employerName = domainVacancy.employerName,
            employerLogoUrl = domainVacancy.employerLogoUrl,
            experience = domainVacancy.experience,
            employment = domainVacancy.employment,
            schedule = domainVacancy.schedule,
            description = domainVacancy.description,
            skills = Gson().toJson(domainVacancy.skills),
            contactEmail = domainVacancy.contactEmail,
            contactName = domainVacancy.contactName,
            contactPhoneNumbers = Gson().toJson(domainVacancy.contactPhoneNumbers),
            contactComment = Gson().toJson(domainVacancy.contactComment),
            timestamp = currentTimestamp,
            url = domainVacancy.url.toString()
        )
    }

    fun map(vacancy: FavoriteVacancyEntity): DomainVacancy {
        return DomainVacancy(
            vacancyId = vacancy.vacancyId,
            name = vacancy.name,
            city = vacancy.city,
            area = vacancy.area,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            salaryCurrency = vacancy.salaryCurrency,
            employerName = vacancy.employerName,
            employerLogoUrl = vacancy.employerLogoUrl,
            experience = vacancy.experience,
            employment = vacancy.employment,
            schedule = vacancy.schedule,
            description = vacancy.description,
            skills = convertStringToList(vacancy.skills),
            contactEmail = vacancy.contactEmail,
            contactName = vacancy.contactName,
            contactPhoneNumbers = convertStringToList(vacancy.contactPhoneNumbers),
            contactComment = convertStringToList(vacancy.contactComment),
            url = vacancy.url,
            isFavorite = true
        )
    }

    private fun convertStringToList(string: String?): List<String> {
        return string?.let {
            Gson().fromJson(it, Array<String>::class.java)?.toList() ?: emptyList()
        } ?: emptyList()
    }
}
