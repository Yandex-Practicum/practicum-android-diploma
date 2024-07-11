package ru.practicum.android.diploma.vacancy.data.dto

import ru.practicum.android.diploma.search.data.dto.toStr
import ru.practicum.android.diploma.vacancy.data.dto.components.KeySkill
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun responseToVacancyFull(response: VacancyResponse): VacancyFull {
    return VacancyFull(
        id = response.id,
        name = response.name,
        company = response.employer.name,
        salary = response.salary?.toStr() ?: "salary: null",
        area = response.area.name,
        alternateUrl = response.alternateUrl,
        icon = response.employer.logoUrls?.logo240 ?: "",
        employment = response.employment?.name ?: "employment null",
        experience = response.experience?.name ?: "experience null",
        schedule = response.schedule.name,
        description = response.description,
        contact = response.contacts?.name ?: "contacts null",
        email = response.contacts?.email ?: "contacts@null.ru",
        phone = response.contacts?.phones?.firstOrNull()?.number ?: "000-00-00",
        comment = response.contacts?.phones?.firstOrNull()?.comment ?: "contacts null",
        keySkills = keySkillsToString(response.keySkills)
    )
}

fun keySkillsToString(keySkills: List<KeySkill>): String {
    var result = ""
    keySkills.forEach { keySkill ->
        result += "• ${keySkill.name}\n"
    }
    return result
}
