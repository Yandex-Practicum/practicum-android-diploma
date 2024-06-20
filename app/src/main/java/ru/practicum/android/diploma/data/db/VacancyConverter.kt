package ru.practicum.android.diploma.data.db

import com.google.gson.Gson
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyConverter {

    fun map(vacancy: Vacancy): FavoriteVacancyEntity {
        val currentTimestamp: Long = java.util.Date().time

        return FavoriteVacancyEntity(
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
            skills = Gson().toJson(vacancy.skills),
            contactEmail = vacancy.contactEmail,
            contactName = vacancy.contactName,
            contactPhoneNumbers = Gson().toJson(vacancy.contactPhoneNumbers),
            contactComment = Gson().toJson(vacancy.contactComment),
            timestamp = currentTimestamp,
            url = vacancy.url
        )
    }

    fun map(vacancy: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
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
