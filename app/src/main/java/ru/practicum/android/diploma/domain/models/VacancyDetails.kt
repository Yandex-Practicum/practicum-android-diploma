package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.domain.models.components.AreaModel
import ru.practicum.android.diploma.domain.models.components.ContactsModel
import ru.practicum.android.diploma.domain.models.components.EmployerModel
import ru.practicum.android.diploma.domain.models.components.EmploymentModel
import ru.practicum.android.diploma.domain.models.components.ExperienceModel
import ru.practicum.android.diploma.domain.models.components.KeySkillsModel
import ru.practicum.android.diploma.domain.models.components.SalaryModel
import ru.practicum.android.diploma.domain.models.components.ScheduleModel

data class VacancyDetails(
    val id: String,
    val area: AreaModel?,
    val employer: EmployerModel?,
    val name: String?,
    val salary: SalaryModel?,
    val description: String?,
    val employment: EmploymentModel?,
    val experience: ExperienceModel?,
    val contacts: ContactsModel?,
    val schedule: ScheduleModel?,
    val keySkills: List<KeySkillsModel>?,
    val alternateUrl: String?
)
