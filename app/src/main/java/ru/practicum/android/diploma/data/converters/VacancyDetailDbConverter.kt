package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entyti.VacancyDetailEntity
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
}

