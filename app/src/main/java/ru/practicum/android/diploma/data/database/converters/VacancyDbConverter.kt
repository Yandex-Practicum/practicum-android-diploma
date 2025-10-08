package ru.practicum.android.diploma.data.database.converters

import com.google.gson.Gson
import ru.practicum.android.diploma.data.database.entity.VacancyDetailEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDetailDbConverter(
    private val gson: Gson
) {
    // Domain -> Entity
    fun map(vacancy: VacancyDetail): VacancyDetailEntity {
        return VacancyDetailEntity(
            id = vacancy.id,
            title = vacancy.name,
            description = vacancy.description,
            salary = gson.toJson(vacancy.salary),
            employer = gson.toJson(vacancy.employer),
            industry = gson.toJson(vacancy.industry),
            area = gson.toJson(vacancy.area),
            experience = gson.toJson(vacancy.experience),
            schedule = gson.toJson(vacancy.schedule),
            employment = gson.toJson(vacancy.employment)
        )
    }

    // Entity -> Domain
    fun map(
        vacancyDetailEntity: VacancyDetailEntity
    ): VacancyDetail {
        return VacancyDetail(
            id = vacancyDetailEntity.id,
            name = vacancyDetailEntity.title,
            description = vacancyDetailEntity.description,
            salary = gson.fromJson(vacancyDetailEntity.salary, Salary::class.java),
            employer = gson.fromJson(vacancyDetailEntity.employer, Employer::class.java),
            industry = gson.fromJson(vacancyDetailEntity.industry, FilterIndustry::class.java),
            area = gson.fromJson(vacancyDetailEntity.area, Area::class.java),
            experience = gson.fromJson(vacancyDetailEntity.experience, Experience::class.java),
            schedule = gson.fromJson(vacancyDetailEntity.schedule, Schedule::class.java),
            employment = gson.fromJson(vacancyDetailEntity.employment, Employment::class.java)
        )
    }

    fun map(vacancyDetailEntityList: List<VacancyDetailEntity>): List<VacancyDetail> {
        return vacancyDetailEntityList.map {
            with(it) {
                VacancyDetail(
                    id = id,
                    name = title,
                    description = description,
                    salary = gson.fromJson(salary, Salary::class.java),
                    employer = gson.fromJson(employer, Employer::class.java),
                    industry = gson.fromJson(industry, FilterIndustry::class.java),
                    area = gson.fromJson(area, Area::class.java),
                    experience = gson.fromJson(experience, Experience::class.java),
                    schedule = gson.fromJson(schedule, Schedule::class.java),
                    employment = gson.fromJson(employment, Employment::class.java)
                )
            }
        }
    }
}
