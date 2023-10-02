package ru.practicum.android.diploma.search.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.fields.Area
import ru.practicum.android.diploma.search.domain.models.fields.Contacts
import ru.practicum.android.diploma.search.domain.models.fields.Employer
import ru.practicum.android.diploma.search.domain.models.fields.Employment
import ru.practicum.android.diploma.search.domain.models.fields.Experience
import ru.practicum.android.diploma.search.domain.models.fields.KeySkill
import ru.practicum.android.diploma.search.domain.models.fields.Salary
import ru.practicum.android.diploma.search.domain.models.fields.Schedule
import ru.practicum.android.diploma.search.domain.models.fields.Type

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    val area: Area?,
    val contacts: Contacts?,
    val description: String?,
    val employer: Employer?,
    val employment: Employment?,
    val experience: Experience?,
    @PrimaryKey
    val id: Long?,
    val keySkills: List<KeySkill>?,
    val name: String,
    val salary: Salary?,
    val schedule: Schedule?,
    val type: Type?,
    val logoUrls: String?,
) {
    fun VacancyEntity.toVacancy(): Vacancy {
        return Vacancy(
            area = this.area,
            contacts = this.contacts,
            description = this.description,
            employer = this.employer,
            employment = this.employment,
            experience = this.experience,
            id = this.id,
            keySkills = this.keySkills,
            name = this.name,
            salary = this.salary,
            schedule = this.schedule,
            type = this.type,
            logoUrls = this.logoUrls
        )
    }
}
