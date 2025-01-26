package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentFormDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.SkillDto
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.EmploymentForm
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.Skill
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesConverter {

    fun convertCut(response: VacanciesResponseDto): List<Vacancy> {
        return response.items.map {
            it.toVacancyCut()
        }
    }

    // для запроса деталей вакансии (задача 30)
    fun convertFull(response: VacancyDto): Vacancy {
        return Vacancy(
            vacancyId = response.vacancyId,
            name = response.name,
            area = response.area?.toArea(),
            employer = response.employer?.toEmployer(),
            salary = response.salary?.toSalary(),
            experience = response.experience?.toExperience(),
            employmentForm = response.employmentForm?.toEmploymentForm(),
            employment = response.employment?.toEmploymentForm(),
            schedule = response.schedule?.toSchedule(),
            description = response.description,
            keySkills = response.keySkills.map { it?.toSkill() },
            alternateUrl = response.alternateUrl,
            address = response.address?.toAddress()
        )
    }

    // для запроса списка вакансий (все поля не нужны, поэтому они null)
    private fun VacancyDto.toVacancyCut(): Vacancy {
        return Vacancy(
            vacancyId = this.vacancyId,
            name = this.name,
            area = this.area?.toArea(),
            employer = this.employer?.toEmployer(),
            salary = this.salary?.toSalary(),
            experience = null,
            employmentForm = null,
            employment = null,
            schedule = null,
            description = null,
            keySkills = emptyList(),
            alternateUrl = null,
            address = this.address?.toAddress()
        )
    }

    private fun AreaDto.toArea(): Area {
        return Area(
            name = this.name,
        )
    }

    private fun EmployerDto.toEmployer(): Employer {
        return Employer(
            name = this.name,
            logoUrls = this.logoUrls?.toLogoUrls()
        )
    }

    private fun LogoUrlsDto.toLogoUrls(): LogoUrls {
        return LogoUrls(
            original = this.original,
            size90 = this.size90,
            size240 = this.size240
        )
    }

    private fun SalaryDto.toSalary(): Salary {
        return Salary(
            currency = this.currency,
            from = this.from,
            to = this.to
        )
    }

    private fun ExperienceDto.toExperience(): Experience {
        return Experience(
            name = this.name
        )
    }

    private fun EmploymentFormDto.toEmploymentForm(): EmploymentForm {
        return EmploymentForm(
            name = this.name
        )
    }

    private fun ScheduleDto.toSchedule(): Schedule {
        return Schedule(
            name = this.name
        )
    }

    private fun SkillDto.toSkill(): Skill {
        return Skill(
            name = this.name
        )
    }

    private fun AddressDto.toAddress(): Address {
        return Address(
            city = this.city
        )
    }
}
