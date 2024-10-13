package ru.practicum.android.diploma.search.data.converters

import ru.practicum.android.diploma.search.data.dto.VacancyItemDto
import ru.practicum.android.diploma.search.data.network.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.models.VacancyListResponseData
import ru.practicum.android.diploma.search.domain.models.VacancySearch

class SearchVacancyNetworkConverter(
    private val salaryCurrencySignFormater: SalaryCurrencySignFormater
) {

    fun map(vacancyItemDto: VacancyItemDto): VacancySearch {
        return VacancySearch(
            id = vacancyItemDto.id,
            name = vacancyItemDto.name,
            address = getAddressFromDto(vacancyItemDto),
            company = vacancyItemDto.employer.name,
            salary = salaryCurrencySignFormater.getStringSalary(vacancyItemDto.salary),
            logo = vacancyItemDto.employer.logoUrls?.size240
        )
    }

    private fun getAddressFromDto(vacancyItemDto: VacancyItemDto): String {
        return vacancyItemDto.address?.city ?: vacancyItemDto.area.name
    }

    fun map(vacancyResponse: VacancySearchResponse): VacancyListResponseData {
        return VacancyListResponseData(
            items = vacancyResponse.items.map { map(it) },
            found = vacancyResponse.found,
            page = vacancyResponse.page,
            pages = vacancyResponse.pages
        )
    }
}
