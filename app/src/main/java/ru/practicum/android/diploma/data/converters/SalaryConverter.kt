package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.domain.models.Salary

fun SalaryDto.toModel(): Salary = Salary(
    salaryFrom = this.from,
    salaryTo = this.to,
    currency = this.currency
)

fun Salary.toDto(): SalaryDto = SalaryDto(
    from = this.salaryFrom,
    to = this.salaryTo,
    currency = this.currency
)
