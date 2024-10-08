package ru.practicum.android.diploma.database.converters

import ru.practicum.android.diploma.database.entities.VacancyDetailsEntity
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDbConverter {
    fun map(vacancyEntity: VacancyDetailsEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            currency = "RUB",
            salary = vacancyEntity.salary,
            companyLogo = null,
            area = vacancyEntity.area,
            address = vacancyEntity.address,
            experience = vacancyEntity.experience,
            schedule = vacancyEntity.schedule,
            employment = vacancyEntity.employment,
            description = vacancyEntity.description,
            keySkills = vacancyEntity.keySkill,
            isFavorite = false
        )
    }
    fun map(vacancy: Vacancy): VacancyDetailsEntity {
        return VacancyDetailsEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = vacancy.salary,
            area = vacancy.area,
            address = vacancy.address,
            experience = vacancy.experience,
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            description = vacancy.description,
            keySkill = vacancy.keySkills
        )
    }
}
