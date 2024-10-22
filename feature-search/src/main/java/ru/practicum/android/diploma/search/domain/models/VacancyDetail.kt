package ru.practicum.android.diploma.search.domain.models

internal data class VacancyDetail(
    val alternateUrl: String?,
    val applyAlternateUrl: String?,
    val area: AreaInVacancy,
    val brandedDescription: String?,
    val description: String,
    val employer: Employer,
    val employment: Employment,
    val experience: Experience,
    val id: String,
    val keySkills: List<KeySkill>,
    val languages: List<Language>,
    val name: String,
    val professionalRoles: List<ProfessionalRole>,
    val salary: Salary,
    val schedule: Schedule,
    val workingDays: List<WorkingDay>,
    val workingTimeIntervals: List<WorkingTimeInterval>,
)
