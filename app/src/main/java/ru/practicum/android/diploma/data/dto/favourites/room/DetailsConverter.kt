package ru.practicum.android.diploma.data.dto.favourites.room

import ru.practicum.android.diploma.domain.models.DetailVacancy

class DetailsConverter {
    fun map(dv: DetailVacancy): VacancyDetails =
        VacancyDetails(
            id = dv.id,
            url = dv.areaUrl.toString(),
            name = dv.name.toString(),
            area = dv.areaName.toString(),
            salaryCurrency = dv.salaryCurrency,
            salaryFrom = dv.salaryFrom,
            salaryTo = dv.salaryTo,
            salaryGross = dv.salaryGross,
            experience = dv.experienceName,
            schedule = dv.scheduleName,
            contactName = dv.contactsName,
            contactEmail = dv.contactsEmail,
            phones = dv.contactsPhones,
            contactComment = dv.comment,
            logoUrl = dv.areaUrl,
            logoUrl90 = dv.areaUrl,
            logoUrl240 = dv.areaUrl,
            employerUrl = dv.employerUrl,
            employerName = dv.employerName,
            employment = dv.employmentId,
            keySkills = dv.keySkillsNames,
            description = dv.description.toString(),
            address = dv.areaName
        )
}
