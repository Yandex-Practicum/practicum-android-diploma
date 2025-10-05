package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.data.database.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {

    // Domain -> Entity
    fun map(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id.toIntOrNull() ?: 0, // String -> Int
            title = vacancy.title,
            description = vacancy.description,
            salary = vacancy.salary?.currency,   // Salary -> String (currency)
            employer = vacancy.employer?.id,     // String?
            area = vacancy.area?.id              // String?
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
            id = vacancyEntity.id.toString(),  // Int -> String
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

