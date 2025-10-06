package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.data.database.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.UUID

class VacancyDbConverter {

    // Domain -> Entity
    fun map(vacancy: Vacancy, area: UUID?, employer: UUID?, salary: UUID?): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id.toIntOrNull() ?: 0,
            title = vacancy.title,
            description = vacancy.description,
            salary = salary,
            employer = employer,
            area = area
        )
    }

    // Entity -> Domain
    fun map(
        vacancyEntity: VacancyEntity,
        salaryEntity: SalaryEntity?,
        employerEntity: EmployerEntity?,
        areaEntity: AreaEntity?
    ): Vacancy {
        return Vacancy(
            id = vacancyEntity.id.toString(),
            title = vacancyEntity.title,
            description = vacancyEntity.description,
            salary = mapSalary(salaryEntity),
            employer = mapEmployee(employerEntity),
            area = mapArea(areaEntity)
        )
    }

    fun map(
        list: List<VacancyEntity>,
        salaryEntity: SalaryEntity?,
        employerEntity: EmployerEntity?,
        areaEntity: AreaEntity?
    ): List<Vacancy> {
        return list.map {
            map(it, salaryEntity, employerEntity, areaEntity)
        }
    }

    private fun mapSalary(salaryEntity: SalaryEntity?): Salary? {
        return salaryEntity?.let { Salary(to = it.to, from = it.from, currency = it.currency) }
    }

    private fun mapEmployee(employerEntity: EmployerEntity?): Employer? {
        return employerEntity?.let { Employer(id = it.id, name = it.name, logoUrl = it.logoUrl) }
    }

    private fun mapArea(areaEntity: AreaEntity?): Area? {
        return areaEntity?.let { Area(id = it.id, name = it.name) }
    }
}
