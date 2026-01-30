package ru.practicum.android.diploma.data.search

import ru.practicum.android.diploma.data.dto.Salary
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy
import java.util.Locale

object VacancyDtoMapper {
    fun map(dto: VacancyDto): Vacancy {
        return Vacancy(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            experience = dto.experience?.name,
            schedule = dto.schedule?.name,
            employment = dto.employment?.name,
            areaName = dto.area.name,
            industryName = dto.industry.name,
            skills = dto.skills,
            url = dto.url,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            currency = dto.salary?.currency,
            salaryTitle = formatSalary(dto.salary),
            city = dto.address?.city,
            street = dto.address?.street,
            building = dto.address?.building,
            fullAddress = dto.address?.fullAddress,
            contactName = dto.contacts?.name,
            email = dto.contacts?.email,
            phones = dto.contacts?.phones?.map { it.formatted },
            employerName = dto.employer.name,
            logoUrl = dto.employer.logo
        )
    }

    fun mapList(dtoList: List<VacancyDto>?): List<Vacancy> {
        return dtoList?.map { map(it) } ?: emptyList()
    }

    fun formatSalary(salary: Salary?): String {
        return if (salary != null) {
            buildString {
                val formattedFrom = salary.from?.let { formatNumber(it) }
                val formattedTo = salary.to?.let { formatNumber(it) }

                when {
                    formattedFrom != null && formattedTo != null ->
                        append("От $formattedFrom до $formattedTo")

                    formattedFrom != null ->
                        append("От $formattedFrom")

                    formattedTo != null ->
                        append("До $formattedTo")

                    else ->
                        return "Зарплата не указана"
                }

                salary.currency?.let { currency ->
                    if (currency.isNotBlank()) {
                        append(" ")
                        append(getCurrencySymbol(currency))
                    }
                }
            }
        } else {
            "Зарплата не указана"
        }
    }

    private fun formatNumber(number: Int): String {
        return String.Companion.format(Locale.getDefault(), "%,d", number)
            .replace(',', ' ')
    }


    private fun getCurrencySymbol(currencyCode: String): String {
        return when (currencyCode.uppercase()) {
            "RUR", "RUB" -> "₽"
            "BYR" -> "Br"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "UZS" -> "сўм"
            "GEL" -> "₾"
            "KGT" -> "сом"
            else -> currencyCode
        }
    }
}

