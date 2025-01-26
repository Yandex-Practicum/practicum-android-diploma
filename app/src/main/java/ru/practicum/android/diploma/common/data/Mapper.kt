package ru.practicum.android.diploma.common.data

import ru.practicum.android.diploma.common.data.dto.SearchVacancyResponse
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

        return map
    }

    fun map(response: SearchVacancyResponse): VacancyList {
        val items: List<VacancyItems> = response.items.map { map(it) }
        return VacancyList(
            items = items,
            found = response.found ?: 0,
            pages = response.pages ?: 0,
            page = response.page ?: 0,
            perPage = response.perPage ?: 0,
        )
    }

    fun map(searchVacancyDto: SearchVacancyDto): VacancyItems {
        return VacancyItems(
            id = searchVacancyDto.id,
            name = searchVacancyDto.name,
            employer = searchVacancyDto.employer?.name ?: "",
            areaName = getAreaName(searchVacancyDto.area),
            iconUrl = getEmployerLogo(searchVacancyDto.employer),
            salary = map(searchVacancyDto.salary),
        )
    }

    fun map(salaryDto: SalaryDto?): Salary? {
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

    private fun getAreaName(areaDto: AreaDto?): String = areaDto?.name ?: ""
    private fun getEmployerLogo(employerDto: EmployerDto?): String? = employerDto?.logoUrls?.iconBig
}
