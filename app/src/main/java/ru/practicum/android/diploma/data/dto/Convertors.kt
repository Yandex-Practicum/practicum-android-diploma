package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.field.KeySkillsDto
import ru.practicum.android.diploma.data.dto.field.PhonesDto
import ru.practicum.android.diploma.domain.model.DetailVacancy

class Convertors {
    fun convertorToDetailVacancy(vacancy: VacancyDetailedDto): DetailVacancy {
        return DetailVacancy(
            id = vacancy.id,
            areaName = vacancy.area?.name,
            areaUrl = vacancy.employer?.logoUrls?.logoUrl240,
            contactsEmail = vacancy.contacts?.email,
            contactsName = vacancy.contacts?.name,
            contactsPhones = vacancy.contacts?.phones.let { list -> list?.map { createPhone(it) } },
            comment = null,
            description = vacancy.description,
            employerName = vacancy.employer?.name,
            employmentName = vacancy.employment.name,
            experienceName = vacancy.experience.name,
            keySkillsNames = createKeySkills(vacancy.keySkills),
            name = vacancy.name,
            salaryCurrency = vacancy.salary?.currency,
            salaryFrom = vacancy.salary?.from,
            salaryGross = false,
            salaryTo = vacancy.salary?.to,
            scheduleId = "",
            scheduleName = vacancy.schedule?.name,
        )
    }
    private fun createPhone(phone: PhonesDto): String {
        return "+${phone.country}" + " (${phone.city})" + " ${phone.number}"
    }
    private fun createKeySkills(keySkills: List<KeySkillsDto>?): List<String?> {
        return keySkills?.map { it.name } ?: emptyList()
    }
}
