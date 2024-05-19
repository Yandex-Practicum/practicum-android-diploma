package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDBConverters {
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salary = "vacancy.salary",
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }

    fun map(vacancy: VacancyEntity): Vacancy {
        return Vacancy(
            id = vacancy.id,
            name = vacancy.name,
            salary = Salary(null, null, null, null),
            city = vacancy.city,
            employerName = vacancy.employerName,
            urlImage = vacancy.urlImage
        )
    }
}
