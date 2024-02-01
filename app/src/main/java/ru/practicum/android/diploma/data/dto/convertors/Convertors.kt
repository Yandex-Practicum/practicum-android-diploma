package ru.practicum.android.diploma.data.dto.convertors

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.field.AreaDto
import ru.practicum.android.diploma.data.dto.field.EmploerDto
import ru.practicum.android.diploma.data.dto.field.LogoUrlDto
import ru.practicum.android.diploma.data.dto.field.SalaryDto
import ru.practicum.android.diploma.domain.models.Vacancy

class Convertors {
    fun convertorToVacancy(vacancy: VacancyDto): Vacancy {
        return Vacancy(
            id = vacancy.id,
            area = createAreaName(vacancy.area),
            employerName = createEmployerName(vacancy.employer),
            name = vacancy.name,
            salary = createSalary(vacancy.salary),
            logoUrl90 = createLogoUrl(createEmployerLogoUrls(vacancy.employer))
        )
    }

    private fun createAreaName(area: AreaDto?): String? {
        return if (area?.name == null) {
            null
        } else {
            area.name
        }
    }

    private fun createEmployerName(employerName: EmploerDto?): String? {
        return if (employerName?.name == null) {
            null
        } else {
            employerName.name
        }
    }
    private fun createEmployerLogoUrls(logoUrls: EmploerDto?): LogoUrlDto? {
        return if (logoUrls?.name == null) {
            null
        } else {
            logoUrls.logoUrls
        }
    }
    private fun createSalary(salary: SalaryDto?): String {
        return R.string.salary_not.toString()
    }

/*
    private fun createSalary(salary: SalaryDto?): String {
        salary ?: return R.string.salary_not.toString()

        return when {
            salary.from != null && salary.to != null && salary.currency != null ->
                with(salary) {
                    "от " +
                        "$from" + " до " +
                        "$to" +
                        "$currency"
                }

            salary.from != null && salary.to == null && salary.currency != null ->
                with(salary) {
                    "от" +
                        "$from" +
                        "$currency"
                }

            salary.from == null && salary.to != null && salary.currency != null ->
                with(salary) {
                    "$to" +
                        "$currency"
                }

            else -> R.string.salary_not.toString()
        }
    }


 */
    private fun createLogoUrl(logo: LogoUrlDto?): String? {
        return if (logo?.logoUrl90 == null) {
            null
        } else {
            logo.logoUrl90
        }
    }
}
