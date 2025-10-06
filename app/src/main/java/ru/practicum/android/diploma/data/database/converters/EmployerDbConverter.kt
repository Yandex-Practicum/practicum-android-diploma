package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.domain.models.Employer

class EmployerDbConverter {
    fun map(employer: Employer): EmployerEntity {
        return EmployerEntity(
            id = employer.id,
            name = employer.name,
            logoUrl = employer.logoUrl
        )
    }

    fun map(employer: EmployerEntity): Employer {
        return Employer(
            id = employer.id,
            name = employer.name,
            logoUrl = employer.logoUrl
        )
    }
}
