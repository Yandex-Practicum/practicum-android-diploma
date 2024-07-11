package ru.practicum.android.diploma.favourites.data.db

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = this.vacancyId,
        name = this.name,
        company = this.company,
        salary = this.salary,
        area = this.area,
        icon = this.icon
    )
}

fun VacancyEntity.toVacancyFull(): VacancyFull {
    return VacancyFull(
        id = this.vacancyId,
        name = this.name,
        company = this.company,
        salary = this.salary,
        area = this.area,
        alternateUrl = this.alternateUrl,
        icon = this.icon,
        employment = this.employment,
        experience = this.experience,
        schedule = this.schedule,
        description = this.description,
        contact = this.contact,
        email = this.email,
        phone = this.phone,
        comment = this.comment,
        keySkills = this.keySkills
    )
}

fun VacancyFull.toVacancyEntity(): VacancyEntity {
    return VacancyEntity(
        id = null,
        vacancyId = this.id,
        name = this.name,
        company = this.company,
        salary = this.salary,
        area = this.area,
        alternateUrl = this.alternateUrl,
        icon = this.icon,
        employment = this.employment,
        experience = this.experience,
        schedule = this.schedule,
        description = this.description,
        contact = this.contact,
        email = this.email,
        phone = this.phone,
        comment = this.comment,
        keySkills = this.keySkills
    )
}
