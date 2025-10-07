package ru.practicum.android.diploma.data.database.converters

import com.google.gson.Gson
import ru.practicum.android.diploma.data.database.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter(
    private val gson: Gson
) {
    // Domain -> Entity
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id.toIntOrNull() ?: 0,
            title = vacancy.title,
            description = vacancy.description,
            salary = gson.toJson(vacancy.salary),
            employer = gson.toJson(vacancy.employer),
            area = gson.toJson(vacancy.area)
        )
    }

    // Entity -> Domain
    fun map(
        vacancyEntity: VacancyEntity
    ): Vacancy {
        return Vacancy(
            id = vacancyEntity.id.toString(),
            title = vacancyEntity.title,
            description = vacancyEntity.description,
            salary = gson.fromJson(vacancyEntity.salary, Salary::class.java),
            employer = gson.fromJson(vacancyEntity.salary, Employer::class.java),
            area = gson.fromJson(vacancyEntity.salary, Area::class.java),
        )
    }

    fun map(vacancyEntityList: List<VacancyEntity>): List<Vacancy> {
        return vacancyEntityList.map {
            with(it) {
                Vacancy(
                    id = it.id.toString(),
                    title = it.title,
                    description = it.description,
                    salary = gson.fromJson(it.salary, Salary::class.java),
                    employer = gson.fromJson(it.employer, Employer::class.java),
                    area = gson.fromJson(it.area, Area::class.java)
                )
            }
        }
    }
}
