package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entity.ShortVacancyEntity
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentFormDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.SkillDto
import ru.practicum.android.diploma.data.dto.VacanciesResponseDto
import ru.practicum.android.diploma.data.dto.VacancyAreaDto
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
import ru.practicum.android.diploma.domain.models.VacancyResponse
import ru.practicum.android.diploma.util.format.JsonUtils
import ru.practicum.android.diploma.util.format.JsonUtils.deserializeField

class VacanciesConverter {

    fun convertCut(response: VacanciesResponseDto): VacancyResponse {
        return VacancyResponse(
            found = response.found,
            response.items.map { it.toVacancyCut() },
            page = response.page,
            pages = response.pages
        )
    }

    // для запроса деталей вакансии
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
            area = null,
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

    fun convertFromDBtoVacancy(entity: VacancyEntity): Vacancy {
        val keySkill = entity.keySkills?.let { JsonUtils.fromJsonList<Skill?>(it) } ?: emptyList()
        return Vacancy(
            vacancyId = entity.vacancyId,
            name = entity.name,
            employer = deserializeField(entity.employer, Employer::class.java),
            salary = deserializeField(entity.salary, Salary::class.java),
            area = deserializeField(entity.area, Area::class.java),
            experience = deserializeField(entity.experience, Experience::class.java),
            employmentForm = deserializeField(entity.employmentForm, EmploymentForm::class.java),
            employment = deserializeField(entity.employmentForm, EmploymentForm::class.java),
            schedule = deserializeField(entity.schedule, Schedule::class.java),
            description = entity.description,
            keySkills = keySkill,
            alternateUrl = entity.alternateUrl,
            address = deserializeField(entity.address, Address::class.java)
        )
    }

    fun convertFromVacancyToDB(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            vacancyId = vacancy.vacancyId,
            name = vacancy.name,
            employer = JsonUtils.toJson(vacancy.employer),
            salary = JsonUtils.toJson(vacancy.salary),
            area = JsonUtils.toJson(vacancy.area),
            experience = JsonUtils.toJson(vacancy.experience),
            employmentForm = JsonUtils.toJson(vacancy.employmentForm),
            employment = JsonUtils.toJson(vacancy.employmentForm),
            schedule = JsonUtils.toJson(vacancy.schedule),
            description = vacancy.description,
            keySkills = JsonUtils.toJson(vacancy.keySkills),
            alternateUrl = vacancy.alternateUrl,
            address = JsonUtils.toJson(vacancy.address),
            timeStamp = System.currentTimeMillis()
        )
    }

    fun convertFromShortEntity(entity: ShortVacancyEntity): Vacancy {
        return Vacancy(
            vacancyId = entity.vacancyId,
            name = entity.name,
            area = null,
            employer = deserializeField(entity.employer, Employer::class.java),
            salary = deserializeField(entity.salary, Salary::class.java),
            experience = null,
            employmentForm = null,
            employment = null,
            schedule = null,
            description = null,
            keySkills = emptyList(),
            alternateUrl = null,
            address = deserializeField(entity.address, Address::class.java),
        )
    }

    private fun VacancyAreaDto.toArea(): Area {
        return Area(
            id = this.id,
            name = this.name,
            parentId = null // в вакансии не нужен parentId
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
