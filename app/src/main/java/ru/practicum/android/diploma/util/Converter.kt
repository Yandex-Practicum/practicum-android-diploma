package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.Vacancy
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

/*
fun convertVacancyDtoToVacancy(vacancyDto: ru.practicum.android.diploma.search.data.dto.VacancyDto): Vacancy {
    return Vacancy(
        id = vacancyDto.id,
        name = vacancyDto.name,
        city = vacancyDto.city,
        employerName = vacancyDto.employerName,
        employerLogoUrl = vacancyDto.employerLogoUrl,
        salaryCurrency = vacancyDto.salaryCurrency,
        salaryFrom = vacancyDto.salaryFrom,
        salaryTo = vacancyDto.salaryTo
    )
}*/

fun createValue(salary: Int?): String? {
    if (salary == null) {
        return null
    } else {
        val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
        val symbols: DecimalFormatSymbols = formatter.getDecimalFormatSymbols()
        symbols.setGroupingSeparator(' ')
        formatter.setDecimalFormatSymbols(symbols)
        return (formatter.format(salary))
    }
}
