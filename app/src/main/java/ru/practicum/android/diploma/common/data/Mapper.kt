package ru.practicum.android.diploma.common.data

import ru.practicum.android.diploma.common.data.dto.CountriesResponse
import ru.practicum.android.diploma.common.data.dto.IndustriesResponse
import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse
import ru.practicum.android.diploma.filter.data.dto.model.CountryDto
import ru.practicum.android.diploma.filter.data.dto.model.IndustryDto
import ru.practicum.android.diploma.search.data.dto.model.AreaDto
import ru.practicum.android.diploma.search.data.dto.model.EmployerDto
import ru.practicum.android.diploma.search.data.dto.model.SalaryDto
import ru.practicum.android.diploma.search.data.dto.model.SearchVacancyDto
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SearchQueryParams
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.domain.model.VacancyList
import ru.practicum.android.diploma.search.presentation.items.ListItem

class Mapper {
    fun map(searchQueryParams: SearchQueryParams): Map<String, String> {
        val map = mutableMapOf<String, String>()

        map["text"] = searchQueryParams.text
        map["page"] = searchQueryParams.page.toString()
        map["per_page"] = "20"

        searchQueryParams.filter?.let { params ->
            (params.areaCity?.id ?: params.areaCountry?.id)?.let { map["area"] = it }
            params.industrySP?.let { map["industry"] = it.id }
            params.salary?.let { map["salary"] = it.toString() }
            params.withSalary?.let { map["only_with_salary"] = it.toString() }
        }
        return map
    }

    fun map(response: SearchVacancyResponse): VacancyList {
        val items: List<VacancyItems> = response.items.map { mapVacancyItems(it) }
        return VacancyList(
            items = items,
            found = response.found ?: 0,
            pages = response.pages ?: 0,
            page = response.page ?: 0,
            perPage = response.perPage ?: 0,
        )
    }

    private fun mapVacancyItems(searchVacancyDto: SearchVacancyDto): VacancyItems {
        return VacancyItems(
            id = searchVacancyDto.id,
            name = searchVacancyDto.name,
            employer = searchVacancyDto.employer?.name ?: "",
            areaName = getAreaName(searchVacancyDto.area),
            iconUrl = getEmployerLogo(searchVacancyDto.employer),
            salary = mapSalary(searchVacancyDto.salary),
        )
    }

    private fun mapSalary(salaryDto: SalaryDto?): Salary? {
        return salaryDto?.let {
            Salary(
                from = it.from,
                to = it.to,
                currency = it.currency ?: ""
            )
        }
    }

    fun map(vacancyItems: VacancyItems): ListItem.Vacancy {
        return with(vacancyItems) {
            ListItem.Vacancy(
                id,
                name,
                areaName,
                employer,
                iconUrl,
                salary
            )
        }
    }

    fun map(list: List<IndustryDto>): IndustriesResponse {
        return IndustriesResponse(
            result = list
        )
    }

    fun map(list: List<CountryDto>): CountriesResponse {
        return CountriesResponse(
            result = list
        )
    }

    private fun getAreaName(areaDto: AreaDto?): String = areaDto?.name ?: ""
    private fun getEmployerLogo(employerDto: EmployerDto?): String? = employerDto?.logoUrls?.iconBig
}
