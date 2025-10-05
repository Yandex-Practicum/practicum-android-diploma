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
    fun map(vacancy: Vacancy): VacancyEntity {
        return with(vacancy) {
            VacancyEntity(
                id = id,
                title = title,
                description = description,
                salary = salary?.currency,
                employer = employer?.id,
                area = area?.id
            )
        }
    }

    fun map(
        vacancyEntity: VacancyEntity,
        salaryEntity: SalaryEntity?,
        employerEntity: EmployerEntity?,
        areaEntity: AreaEntity?
    ): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id,
                title = title,
                description = description,
                salary = mapSalary(salaryEntity = salaryEntity),
                employer = mapEmployee(employerEntity = employerEntity),
                area = mapArea(areaEntity = areaEntity)
            )
        }
    }

    fun map(
        list: List<VacancyEntity>,
        salaryEntity: SalaryEntity?,
        employerEntity: EmployerEntity?,
        areaEntity: AreaEntity?
    ): List<Vacancy> {
        return list.map {
            with(it) {
                Vacancy(
                    id = id,
                    title = title,
                    description = description,
                    salary = mapSalary(salaryEntity = salaryEntity),
                    employer = mapEmployee(employerEntity = employerEntity),
                    area = mapArea(areaEntity = areaEntity)
                )
            }
        }
    }

    private fun mapSalary(salaryEntity: SalaryEntity?): Salary? {
        return if (salaryEntity != null) {
            Salary(to = salaryEntity.to, from = salaryEntity.from, currency = salaryEntity.currency)
        } else {
            null
        }
    }

    private fun mapArea(areaEntity: AreaEntity?): Area? {
        return if (areaEntity != null) {
            Area(id = areaEntity.id, name = areaEntity.name)
        } else {
            null
        }
    }

    private fun mapEmployee(employerEntity: EmployerEntity?): Employer? {
        return if (employerEntity != null) {
            Employer(id = employerEntity.id, name = employerEntity.name, logoUrl = employerEntity.logoUrl)
        } else {
            null
        }
    }
}
