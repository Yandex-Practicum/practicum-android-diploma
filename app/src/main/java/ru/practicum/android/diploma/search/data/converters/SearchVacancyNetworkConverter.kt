package ru.practicum.android.diploma.search.data.converters

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.dto.VacancyItemDto
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class SearchVacancyNetworkConverter(private val context: Context) {

    fun map(vacancyItemDto: VacancyItemDto): VacancySearch {
        return VacancySearch(
            name = vacancyItemDto.name,
            address = getAddressFromDto(vacancyItemDto),
            company = vacancyItemDto.employer.name,
            salary = getSalaryFromDto(vacancyItemDto),
            logo = vacancyItemDto.employer.logoUrls?.size90
        )
    }

    private fun getAddressFromDto(vacancyItemDto: VacancyItemDto): String {
        return if (vacancyItemDto.address?.city == null) {
            vacancyItemDto.area.name
        } else {
            vacancyItemDto.address.city
        }
    }

    private fun getSalaryFromDto(vacancyItemDto: VacancyItemDto): String? {
        val salary = vacancyItemDto.salary
        val from = context.getString(R.string.salary_from)
        val to = context.getString(R.string.salary_to)

        return if (salary == null) {
            null
        } else if (salary.to == null && salary.from != null) {
            "$from ${salary.from}"
        } else if (salary.to != null && salary.from == null) {
            "$to ${salary.to}"
        } else if (salary.to != null && salary.from != null) {
            "$from ${salary.from} $to ${salary.to}"
        } else {
            null
        }
    }
}
