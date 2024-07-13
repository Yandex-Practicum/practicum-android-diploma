package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.search.data.dto.toStr
import ru.practicum.android.diploma.vacancy.data.dto.components.KeySkill
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun responseToVacancyFull(response: VacancyResponse): VacancyFull = with(response) {
    VacancyFull(
        id = id,
        name = name,
        company = employer.name,
        salary = emptyStringIfNull(salary?.toStr()),
        area = area.name,
        alternateUrl = alternateUrl,
        icon = emptyStringIfNull(employer.logoUrls?.logo240),
        employment = emptyStringIfNull(employment?.name),
        experience = emptyStringIfNull(experience?.name),
        schedule = emptyStringIfNull(schedule?.name),
        description = description,
        contact = emptyStringIfNull(contacts?.name),
        email = emptyStringIfNull(contacts?.email),
        phone = emptyStringIfNull(contacts?.phones?.firstOrNull()?.formatted),
        comment = emptyStringIfNull(contacts?.phones?.firstOrNull()?.comment),
        keySkills = keySkillsToString(keySkills),
        address = emptyStringIfNull(address?.raw),
    )
}

private fun emptyStringIfNull(value: String?) = value ?: ""

fun keySkillsToString(keySkills: List<KeySkill>): String {
    var result = ""
    keySkills.forEach { keySkill ->
        result += "• ${keySkill.name}\n"
    }
    return result
}
