package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancies.item.Item
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.HHVacancyDetailResponse
import ru.practicum.android.diploma.search.domain.models.AreaInVacancy
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Employment
import ru.practicum.android.diploma.search.domain.models.Experience
import ru.practicum.android.diploma.search.domain.models.KeySkill
import ru.practicum.android.diploma.search.domain.models.Language
import ru.practicum.android.diploma.search.domain.models.Level
import ru.practicum.android.diploma.search.domain.models.LogoUrls
import ru.practicum.android.diploma.search.domain.models.ProfessionalRole
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.Schedule
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.models.VacancyDetail
import ru.practicum.android.diploma.search.domain.models.WorkingDay
import ru.practicum.android.diploma.search.domain.models.WorkingTimeInterval
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.area.AreaInVacancy as AreaInVacancyDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.employer.Employer as EmployerDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.employer.LogoUrls as LogoUrlsDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.role.ProfessionalRole as ProfessionalRoleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.salary.Salary as SalaryDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.common.schedule.Schedule as ScheduleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.employment.Employment as EmploymentDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.experience.Experience as ExperienceDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.language.Language as LanguageDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.language.Level as LevelDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.skill.KeySkill as KeySkillDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.working.WorkingDay as WorkingDayDto
import ru.practicum.android.diploma.data.networkclient.api.dto.response.vacancydetail.working.WorkingTimeInterval as WorkingTimeIntervalDto

internal class VacancyConverter {
    fun mapItem(items: List<Item>): List<Vacancy> {
        return ArrayList(items.map {
            with(it) {
                Vacancy(
                    id = id,
                    title = name,
                    area = map(area),
                    companyName = employer.name,
                    salaryMin = salary?.from,
                    salaryMax = salary?.to,
                    salaryCurrency = salary?.currency,
                    companyLogo = employer.logoUrls?.original
                )
            }
        })
    }

    fun map(item: HHVacancyDetailResponse): VacancyDetail {
        return with(item) {
            VacancyDetail(
                alternateUrl = alternateUrl,
                applyAlternateUrl = applyAlternateUrl,
                area = map(area),
                brandedDescription = brandedDescription,
                description = description,
                employer = map(employer),
                employment = mapEmployment(employment),
                experience = mapExperience(experience),
                id = id,
                keySkills = mapSkills(keySkills),
                languages = mapLanguage(languages),
                name = name,
                professionalRoles = mapRoles(professionalRoles),
                salary = salary?.let { mapSalary(it) },
                schedule = mapSchedule(schedule),
                workingDays = mapDays(workingDays),
                workingTimeIntervals = map(workingTimeIntervals)
            )
        }
    }

    private fun map(area: AreaInVacancyDto): AreaInVacancy {
        return AreaInVacancy(id = area.id, name = area.name, url = area.url)
    }

    private fun map(employer: EmployerDto): Employer {
        return with(employer) {
            Employer(accreditedITEmployer, alternateUrl, id, map(logoUrls), name, trusted, url)
        }
    }

    private fun map(logoUrls: LogoUrlsDto?): LogoUrls? {
        return if (logoUrls != null) {
            with(logoUrls) {
                LogoUrls(deg240, deg90, original)
            }
        } else {
            null
        }
    }

    private fun mapEmployment(employment: EmploymentDto): Employment {
        return with(employment) {
            Employment(id, name)
        }
    }

    private fun mapExperience(experience: ExperienceDto): Experience {
        return with(experience) {
            Experience(id, name)
        }
    }

    private fun mapSkills(keySkills: List<KeySkillDto>): List<KeySkill> {
        return ArrayList(keySkills).map {
            with(it) {
                KeySkill(name)
            }
        }
    }

    private fun mapLanguage(languages: List<LanguageDto>): List<Language> {
        return ArrayList(languages).map {
            with(it) {
                Language(id, mapLevel(level), name)
            }
        }
    }

    private fun mapLevel(level: LevelDto): Level {
        return with(level) {
            Level(id, name)
        }
    }

    private fun mapRoles(professionalRoles: List<ProfessionalRoleDto>): List<ProfessionalRole> {
        return ArrayList(professionalRoles).map {
            with(it) {
                ProfessionalRole(id, name)
            }
        }
    }

    private fun mapSalary(salary: SalaryDto): Salary {
        return with(salary) {
            Salary(currency, from, gross, to)
        }
    }

    private fun mapSchedule(schedule: ScheduleDto): Schedule {
        return with(schedule) {
            Schedule(id, name)
        }
    }

    private fun mapDays(workingDays: List<WorkingDayDto>): List<WorkingDay> {
        return ArrayList(workingDays).map {
            with(it) {
                WorkingDay(id, name)
            }
        }
    }

    private fun map(workingTimeIntervals: List<WorkingTimeIntervalDto>): List<WorkingTimeInterval> {
        return ArrayList(workingTimeIntervals).map {
            with(it) {
                WorkingTimeInterval(id, name)
            }
        }
    }
}
