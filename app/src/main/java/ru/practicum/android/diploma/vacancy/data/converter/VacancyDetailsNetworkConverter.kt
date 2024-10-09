package ru.practicum.android.diploma.vacancy.data.converter

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.converters.SalaryCurrencySignFormater
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.dto.SalaryDto
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDetailsNetworkConverter(private val context: Context) {
    fun map(response: VacancyDetailsResponse): Vacancy {
        return Vacancy(
            id = response.id,
            name = response.name,
            salary = getSalary(response.salary),
            companyLogo = getLogo(response.employer),
            companyName = getCompanyName(response.employer),
            area = response.area.name,
            address = getAddress(response),
            experience = getExperience(response.experience),
            schedule = response.schedule?.name,
            employment = response.employment?.name,
            description = response.description,
            keySkills = getKeySkills(response.keySkills),
            isFavorite = false
        )
    }

    private fun getKeySkills(keySkills: List<KeySkillDto>): String {
        var keySkillsString = ""
        val bulletDot = context.getString(R.string.bullet_dot)
        keySkills.forEach { skill ->
            keySkillsString += "$bulletDot; ${skill.name} <br/>"
        }
        return keySkillsString
    }

    private fun getExperience(experience: ExperienceDto?): String? {
        return experience?.name
    }

    private fun getLogo(employer: EmployerDto?): String? {
        return employer?.logoUrls?.size240
    }

    private fun getAddress(response: VacancyDetailsResponse): String {
        return if (response.address?.city == null) {
            response.area.name
        } else {
            response.address.city
        }
    }

    private fun getSalary(salaryDto: SalaryDto?): String {
        val salaryFormatter = SalaryCurrencySignFormater(context)
        return salaryFormatter.getStringSalary(salaryDto)
    }

    private fun getCompanyName(employer: EmployerDto?): String {
        return employer?.name ?: ""
    }
}
