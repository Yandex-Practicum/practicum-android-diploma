package ru.practicum.android.diploma.details.data

import ru.practicum.android.diploma.details.domain.models.Contacts
import ru.practicum.android.diploma.details.domain.models.Employer
import ru.practicum.android.diploma.details.domain.models.Experience
import ru.practicum.android.diploma.details.domain.models.KeySkill
import ru.practicum.android.diploma.details.domain.models.Schedule
import ru.practicum.android.diploma.search.data.dto.Response

data class VacancyDetailsResponse (
    val contacts: Contacts?,
    val description: String,
    val employer: Employer?,
    val experience: Experience?,
    val key_skills: Array<KeySkill>,
    val schedule: Schedule?,
): Response()