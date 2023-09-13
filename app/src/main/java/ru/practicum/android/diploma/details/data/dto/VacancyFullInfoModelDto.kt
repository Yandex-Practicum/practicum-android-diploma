package ru.practicum.android.diploma.details.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.details.data.dto.assistants.ContactsDto
import ru.practicum.android.diploma.details.data.dto.assistants.EmploymentDto
import ru.practicum.android.diploma.details.data.dto.assistants.ExperienceDto
import ru.practicum.android.diploma.details.data.dto.assistants.KeySkillDto
import ru.practicum.android.diploma.details.data.dto.assistants.ScheduleDto
import ru.practicum.android.diploma.search.data.network.dto.general_models.Area
import ru.practicum.android.diploma.search.data.network.dto.general_models.Employer
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary

@Serializable
data class VacancyFullInfoModelDto(
    @SerialName("id") val id: String,
    @SerialName("experience") val experience: ExperienceDto?,
    @SerialName("employment") val employment: EmploymentDto?,
    @SerialName("schedule") val schedule: ScheduleDto?,
    @SerialName("description") val description: String?,
    @SerialName("key_skills") val keySkills: List<KeySkillDto>?,
    @SerialName("contacts") val contacts: ContactsDto?,
    @SerialName("area") val area: Area?,
    @SerialName("salary") val salary: Salary?,
    @SerialName("name") val name: String?,
    @SerialName("employer") val employer: Employer?,
    @SerialName("alternate_url") val alternateUrl: String?,
) {

    companion object {
        val empty = VacancyFullInfoModelDto(
            id = "",
            experience = null,
            employment = null,
            schedule = null,
            description = null,
            keySkills = null,
            contacts = null,
            area = null,
            salary = null,
            name = null,
            employer = null,
            alternateUrl = null,
        )
    }
}
