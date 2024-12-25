package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyConverter {

    fun map(vacancy: Vacancy): FavouritesVacancyEntity {
        return FavouritesVacancyEntity(
            id = vacancy.id,
            pictureOfCompanyLogoUri = vacancy.employerLogoUrl,
            titleOfVacancy = vacancy.titleOfVacancy,
            companyName = vacancy.employerName,
            salary = vacancy.salary,
            address = vacancy.regionName,
            experience = vacancy.experience,
            employmentType = vacancy.employmentType,
            scheduleType = vacancy.scheduleType,
            keySkills = vacancy.keySkills,
            vacancyDescription = vacancy.description,
            vacancyUrl = vacancy.alternateUrl
        )
    }

    fun map(vacancyEntity: FavouritesVacancyEntity): Vacancy {
        return Vacancy(
            id = vacancyEntity.id,
            titleOfVacancy = vacancyEntity.titleOfVacancy,
            regionName = vacancyEntity.address,
            salary = vacancyEntity.salary,
            employerName = vacancyEntity.companyName,
            employerLogoUrl = vacancyEntity.pictureOfCompanyLogoUri,
            experience = vacancyEntity.experience,
            employmentType = vacancyEntity.employmentType,
            scheduleType = vacancyEntity.scheduleType,
            keySkills = vacancyEntity.keySkills,
            description = vacancyEntity.vacancyDescription,
            alternateUrl = vacancyEntity.vacancyUrl
        )
    }
}
