package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.search.data.dto.toStr
import ru.practicum.android.diploma.vacancy.data.dto.components.KeySkill
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun responseToVacancyFull(response: VacancyResponse): VacancyFull = with(response) {
    VacancyFull(
        id = id,
        name = name,
        company = employer.name,
        salary = salary?.toStr() ?: "",
        area = area.name,
        alternateUrl = alternateUrl,
        icon = employer.logoUrls?.logo240 ?: "",
        employment = employment?.name ?: "",
        experience = experience?.name ?: "",
        schedule = schedule?.name ?: "",
        description = description,
        contact = contacts?.name ?: "",
        email = contacts?.email ?: "",
        phone = contacts?.phones?.firstOrNull()?.formatted ?: "",
        comment = contacts?.phones?.firstOrNull()?.comment ?: "",
        keySkills = keySkillsToString(keySkills),
        address = address?.raw ?: ""
    )
}

fun keySkillsToString(keySkills: List<KeySkill>): String {
    var result = ""
    keySkills.forEach { keySkill ->
        result += "• ${keySkill.name}\n"
    }
    return result
}
