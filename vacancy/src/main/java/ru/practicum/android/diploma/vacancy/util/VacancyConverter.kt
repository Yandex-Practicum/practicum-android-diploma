package ru.practicum.android.diploma.vacancy.util

import ru.practicum.android.diploma.data.networkclient.api.dto.HHVacancyDetailResponse
import ru.practicum.android.diploma.vacancy.domain.model.Area
import ru.practicum.android.diploma.vacancy.domain.model.Employer
import ru.practicum.android.diploma.vacancy.domain.model.Employment
import ru.practicum.android.diploma.vacancy.domain.model.Experience
import ru.practicum.android.diploma.vacancy.domain.model.KeySkill
import ru.practicum.android.diploma.vacancy.domain.model.Language
import ru.practicum.android.diploma.vacancy.domain.model.Level
import ru.practicum.android.diploma.vacancy.domain.model.LogoUrls
import ru.practicum.android.diploma.vacancy.domain.model.ProfessionalRole
import ru.practicum.android.diploma.vacancy.domain.model.Salary
import ru.practicum.android.diploma.vacancy.domain.model.Schedule
import ru.practicum.android.diploma.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.model.WorkingDay
import ru.practicum.android.diploma.vacancy.domain.model.WorkingTimeInterval
import ru.practicum.android.diploma.data.networkclient.api.dto.Area as AriaDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Employer as EmployerDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Employment as EmploymentDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Experience as ExperienceDto
import ru.practicum.android.diploma.data.networkclient.api.dto.KeySkill as KeySkillDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Language as LanguageDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Level as LevelDto
import ru.practicum.android.diploma.data.networkclient.api.dto.LogoUrls as LogoUrlsDto
import ru.practicum.android.diploma.data.networkclient.api.dto.ProfessionalRole as ProfessionalRoleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Salary as SalaryDto
import ru.practicum.android.diploma.data.networkclient.api.dto.Schedule as ScheduleDto
import ru.practicum.android.diploma.data.networkclient.api.dto.WorkingDay as WorkingDayDto
import ru.practicum.android.diploma.data.networkclient.api.dto.WorkingTimeInterval as WorkingTimeIntervalDto

class VacancyConverter {
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
                salary = mapSalary(salary),
                schedule = mapSchedule(schedule),
                workingDays = mapDays(workingDays),
                workingTimeIntervals = map(workingTimeIntervals)
            )
        }
    }

    private fun map(area: AriaDto): Area {
        return with(area) {
            Area(id = area.id, name = area.name, url = area.url)
        }
    }

    private fun map(employer: EmployerDto): Employer {
        return with(employer) {
            Employer(
                accreditedITEmployer = accreditedITEmployer,
                alternateUrl = alternateUrl,
                id = id,
                logoUrls = map(logoUrls),
                name = name,
                trusted = trusted,
                url = url
            )
        }
    }

    private fun map(logoUrls: LogoUrlsDto?): LogoUrls? {
        return if (logoUrls != null) {
            with(logoUrls) {
                LogoUrls(deg240 = deg240, deg90 = deg90, original = original)
            }
        } else {
            null
        }
    }

    private fun mapEmployment(employment: EmploymentDto): Employment {
        return with(employment) {
            Employment(id = id, name = name)
        }
    }

    private fun mapExperience(experience: ExperienceDto): Experience {
        return with(experience) {
            Experience(id = id, name = name)
        }
    }

    private fun mapSkills(keySkills: List<KeySkillDto>): List<KeySkill> {
        return ArrayList(keySkills).map {
            with(it) {
                KeySkill(name = name)
            }
        }
    }

    private fun mapLanguage(languages: List<LanguageDto>): List<Language> {
        return ArrayList(languages).map {
            with(it) {
                Language(id = id, level = mapLevel(level), name = name)
            }
        }
    }

    private fun mapLevel(level: LevelDto): Level {
        return with(level) {
            Level(id = id, name = name)
        }
    }

    private fun mapRoles(professionalRoles: List<ProfessionalRoleDto>): List<ProfessionalRole> {
        return ArrayList(professionalRoles).map {
            with(it) {
                ProfessionalRole(id = id, name = name)
            }
        }
    }

    private fun mapSalary(salary: SalaryDto): Salary {
        return with(salary) {
            Salary(currency = currency, from = from, gross = gross, to = to)
        }
    }

    private fun mapSchedule(schedule: ScheduleDto): Schedule {
        return with(schedule) {
            Schedule(id = id, name = name)
        }
    }

    private fun mapDays(workingDays: List<WorkingDayDto>): List<WorkingDay> {
        return ArrayList(workingDays).map {
            with(it) {
                WorkingDay(id = id, name = name)
            }
        }
    }

    private fun map(workingTimeIntervals: List<WorkingTimeIntervalDto>): List<WorkingTimeInterval> {
        return ArrayList(workingTimeIntervals).map {
            with(it) {
                WorkingTimeInterval(id = id, name = name)
            }
        }
    }
}
