package ru.practicum.android.diploma.vacancy.data.converter

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.util.Converter.convertSalaryToString
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.vacancy.data.dto.EmployerDto
import ru.practicum.android.diploma.vacancy.data.dto.ExperienceDto
import ru.practicum.android.diploma.vacancy.data.dto.KeySkillDto
import ru.practicum.android.diploma.vacancy.data.network.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyConverter(private val context: Context) {
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
            vacancyUrl = response.alternateUrl
        )
    }

    fun mapDetailsToFavorite(response: VacancyDetailsResponse): VacancyFavorite {
        return VacancyFavorite(
            id = response.id,
            name = response.name,
            salary = response.salary,
            companyLogo = getLogo(response.employer),
            companyName = getCompanyName(response.employer),
            area = response.area.name,
            address = getAddress(response),
            experience = getExperience(response.experience),
            schedule = response.schedule?.name,
            employment = response.employment?.name,
            description = response.description,
            keySkills = getKeySkills(response.keySkills),
            vacancyUrl = response.alternateUrl
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
        return employer?.logoUrls?.iconBig
    }

    private fun getAddress(response: VacancyDetailsResponse): String {
        return if (response.address?.city == null) {
            response.area.name
        } else {
            response.address.city
        }
    }

    private fun getSalary(salaryDto: Salary?): String {
        val salaryFormatter = convertSalaryToString(salaryDto?.from, salaryDto?.to, salaryDto?.currency)
        return salaryFormatter
    }

    private fun getCompanyName(employer: EmployerDto?): String {
        return employer?.name ?: ""
    }
}
