package ru.practicum.android.diploma.data.dto.convertors

import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.data.dto.field.AreaDto
import ru.practicum.android.diploma.domain.models.Vacancy

class Convertors {
    fun convertorToVacancy(vacancy: VacancyDto): Vacancy {
        return Vacancy(
            id = vacancy.id,
            areaId = "",
            areaName = createAreaName(vacancy.area),
            areaUrl = "",
            contactsCallTrackingEnabled = false,
            contactsEmail = "",
            contactsName = "",
            contactsPhones = listOf<String>(),
            description = "",
            employmentId = "",
            employmentName = "",
            experienceId = "",
            experienceName = "",
            keySkillsNames = listOf(),
            name = vacancy.name,
            salaryCurrency = "",
            salaryFrom = 10,
            salaryGross = false,
            salaryTo = 20,
            scheduleId = "",
            scheduleName = "",
        )
    }

    private fun createAreaName(area: AreaDto?): String? {
        return if (area?.name == null) {
            null
        } else {
            area.name
        }
    }
    /*
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



        private fun createLogoUrl(logo: LogoUrlDto?): String? {
            return if (logo?.logoUrl90 == null) {
                null
            } else {
                logo.logoUrl90
            }
        }

     */
}
