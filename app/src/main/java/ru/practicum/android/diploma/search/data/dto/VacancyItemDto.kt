package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.util.dto.AddressDto
import ru.practicum.android.diploma.util.dto.AreaDto
import ru.practicum.android.diploma.util.dto.EmployerDto
import ru.practicum.android.diploma.util.dto.SalaryDto

data class VacancyItemDto(
    val address: AddressDto,
    val area: AreaDto,
    val employer: EmployerDto,
    val name: String,
    val salary: SalaryDto,
)
