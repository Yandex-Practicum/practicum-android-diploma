package ru.practicum.android.diploma.vacancy.data.converter

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.vacancy.data.dto.AddressDto
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.EmploymentDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.dto.SalaryDto
import ru.practicum.android.diploma.vacancy.data.dto.ScheduleDto
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDetailsNetworkConverter(private val context: Context) {
    fun map(response: VacancyDetailsResponse): Vacancy {
        return Vacancy(
            id = response.id,
            name = response.name,
            currency = getCurrency(response.salary),
            salary = getSalary(response.salary),
            companyLogo = getLogo(response.employer),
            area = response.area.name,
            address = getAddress(response.address),
            experience = getExpierence(response.experience),
            schedule = getSchedule(response.schedule),
            employment = getEmployment(response.employment),
            description = response.description,
            keySkills = getKeySkills(response.keySkills),
            isFavorite = false
        )
    }

    private fun getKeySkills(keySkills: List<KeySkillDto>): String {
        val keySkillsString: String = ""
        keySkills.forEach { skill ->
            keySkillsString + skill.name
        }
        return keySkillsString
    }


    private fun getEmployment(employment: EmploymentDto?): String? {
        return employment?.name
    }


    private fun getSchedule(schedule: ScheduleDto?): String? {
        return schedule?.name
    }

    private fun getExpierence(experience: ExperienceDto?): String? {
        return experience?.name
    }

    private fun getLogo(employer: EmployerDto?): String {
        return (!employer?.logoUrls?.original.isNullOrEmpty()).toString()
    }

    private fun getAddress(address: AddressDto?): String {
        return address?.city.toString()
    }


    private fun getSalary(salaryDto: SalaryDto?): String? {
        val from = context.getString(R.string.salary_from)
        val to = context.getString(R.string.salary_to)

        return if (salaryDto == null) {
            null
        } else if (salaryDto.to == null && salaryDto.from != null) {
            "$from ${salaryDto.from}"
        } else if (salaryDto.to != null && salaryDto.from == null) {
            "$to ${salaryDto.to}"
        } else if (salaryDto.to != null && salaryDto.from != null) {
            "$from ${salaryDto.from} $to ${salaryDto.to}"
        } else {
            null
        }
    }

    private fun getCurrency(salaryDto: SalaryDto?): String {
        var currency = "RUB"
        if (!salaryDto?.currency.isNullOrEmpty())
            currency = salaryDto?.currency.toString()
        return currency
    }
}
