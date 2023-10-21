package ru.practicum.android.diploma.feature.search.domain.models

data class VacancyFull(
    val acceptHandicapped: Boolean?,
    val acceptIncompleteResumes: Boolean?,
    val acceptKids: Boolean?,
    val acceptTemporary: Boolean?,
    val address: Any?,
    val allowMessages: Boolean?,
    val alternateUrl: String?,
    val applyAlternateUrl: String?,
    val archived: Boolean?,
    val area: Area?, //
    val billingType: BillingType?,
    val brandedDescription: Any?,
    val code: Any?,
    val contacts: Contacts?, //
    val createdAt: String?,
    val department: Any?,
    val description: String?, // Содержит xml!!!
    val driverLicenseTypes: List<Any>?,
    val employer: Employer?, //
    val employment: Employment?, //
    val experience: Experience?, //
    val hasTest: Boolean?,
    val hidden: Boolean?,
    val id: String?,
    val initialCreatedAt: String?,
    val insiderInterview: Any?,
    val keySkills: List<KeySkill>?, //
    val languages: List<Any>?,
    val name: String?, //
    val negotiationsUrl: Any?,
    val premium: Boolean?,
    val professionalRoles: List<ProfessionalRole>?,
    val publishedAt: String?,
    val quickResponsesAllowed: Boolean?,
    val relations: List<Any>?,
    val responseLetterRequired: Boolean?,
    val responseUrl: Any?,
    val salary: Salary?, //
    val schedule: Schedule?, //
    val specializations: List<Any>?,
    val suitableResumesUrl: Any?,
    val test: Any?,
    val type: Type?,
    val vacancyConstructorTemplate: Any?,
    val workingDays: List<Any>?,
    val workingTimeIntervals: List<Any>?,
    val workingTimeModes: List<Any>?
)