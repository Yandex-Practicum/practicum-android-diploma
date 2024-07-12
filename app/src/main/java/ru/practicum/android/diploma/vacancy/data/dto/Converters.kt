package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.search.data.dto.toStr
import ru.practicum.android.diploma.vacancy.data.dto.components.KeySkill
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun responseToVacancyFull(response: VacancyResponse): VacancyFull {
    return VacancyFull(
        id = response.id,
        name = response.name,
        company = response.employer.name,
        salary = response.salary?.toStr() ?: "",
        area = response.area.name,
        alternateUrl = response.alternateUrl,
        icon = response.employer.logoUrls?.logo240 ?: "",
        employment = response.employment?.name ?: "",
        experience = response.experience?.name ?: "",
        schedule = response.schedule?.name ?: "",
        description = response.description,
        contact = response.contacts?.name ?: "",
        email = response.contacts?.email ?: "",
        phone = response.contacts?.phones?.firstOrNull()?.formatted ?: "",
        comment = response.contacts?.phones?.firstOrNull()?.comment ?: "",
        keySkills = keySkillsToString(response.keySkills),
        address = response.address?.raw ?: ""
    )
}

fun keySkillsToString(keySkills: List<KeySkill>): String {
    var result = ""
    keySkills.forEach { keySkill ->
        result += "• ${keySkill.name}\n"
    }
    return result
}
