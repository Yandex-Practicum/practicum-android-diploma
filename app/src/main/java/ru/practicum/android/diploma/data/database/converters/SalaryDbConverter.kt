package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.domain.models.Salary

class SalaryDbConverter {
    fun map(salary:Salary):SalaryEntity{
        return SalaryEntity(
            from = salary.from,
            to = salary.to,
            currency = salary.currency
        )
    }

    fun map(salary:SalaryEntity):Salary{
        return Salary(
            from = salary.from,
            to = salary.to,
            currency = salary.currency
        )
    }
}
