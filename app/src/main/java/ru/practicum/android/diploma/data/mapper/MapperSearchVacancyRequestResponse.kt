package ru.practicum.android.diploma.data.mapper

import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesRequest
import ru.practicum.android.diploma.data.network.dto.SearchVacanciesResponse
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.ReceivedVacanciesData
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.SearchVacanciesParam
import ru.practicum.android.diploma.domain.models.Vacancy

class MapperSearchVacancyRequestResponse {
    fun mapRequest(expression: String, searchVacanciesParam: SearchVacanciesParam): SearchVacanciesRequest {
        return SearchVacanciesRequest(
            text = expression,
            areaIDs = searchVacanciesParam.areaIDs,
            industryIDs = searchVacanciesParam.industryIDs,
            salary = searchVacanciesParam.salary,
            onlyWithSalary = searchVacanciesParam.onlyWithSalary,
            page = searchVacanciesParam.page,
            perPage = searchVacanciesParam.perPage
        )
    }

    private fun mapLogoUrls(logoUrls: LogoUrlsDto?): LogoUrls {
        return LogoUrls(size90 = logoUrls?.size90, size240 = logoUrls?.size240, original = logoUrls?.original)
    }

    private fun mapEmployer(employer: EmployerDto): Employer {
        return Employer(id = employer.id, logoUrls = mapLogoUrls(employer.logoUrls), name = employer.name)
    }

    private fun mapSalary(salary: SalaryDto): Salary {
        return Salary(currency = salary.currency, from = salary.from, gross = salary.gross, to = salary.to)
    }

    fun mapResponse(searchVacanciesResponse: SearchVacanciesResponse): ReceivedVacanciesData {
        return ReceivedVacanciesData(
            found = searchVacanciesResponse.found,
            items = searchVacanciesResponse.items.map {
                Vacancy(
                    id = it.id,
                    name = it.name,
                    employer = it.employer?.let { it1 -> mapEmployer(it1) },
                    salary = it.salary?.let { it1 -> mapSalary(it1) },
                    salaryRange = it.salary?.let { it1 -> mapSalary(it1) }
                )
            },
            page = searchVacanciesResponse.page,
            pages = searchVacanciesResponse.pages,
            perPage = searchVacanciesResponse.perPage
        )
    }

}
