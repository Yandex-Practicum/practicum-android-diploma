package ru.practicum.android.diploma.search.domain.models

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.search.domain.models.assistants.Contacts
import ru.practicum.android.diploma.search.domain.models.assistants.Employment
import ru.practicum.android.diploma.search.domain.models.assistants.Experience
import ru.practicum.android.diploma.search.domain.models.assistants.KeySkill
import ru.practicum.android.diploma.search.domain.models.assistants.Schedule

/** Модель для получения полной информации о вакансии */
data class VacancyFullInfoModel(
    val id: String,
    /** требуемый опыт работы */
    val experience: Experience?,
    /** Занятость */
    val employment: Employment?,
    val schedule: Schedule?,
    /** Обязанности, условия, требования */
    val description: String?,
    /** Ключевые навыки */
    @SerializedName("key_skills") val keySkills: List<KeySkill>?,
    /** Контакты */
    val contacts: Contacts?,
)
