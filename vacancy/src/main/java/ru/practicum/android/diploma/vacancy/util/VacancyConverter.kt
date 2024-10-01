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
                alternateUrl,
                applyAlternateUrl,
                map(area),
                brandedDescription,
                description,
                map(employer),
                mapEmployment(employment),
                mapExperience(experience),
                id,
                mapSkills(keySkills),
                mapLanguage(languages),
                name,
                mapRoles(professionalRoles),
                mapSalary(salary),
                mapSchedule(schedule),
                mapDays(workingDays),
                map(workingTimeIntervals)
            )
        }
    }

    private fun map(area: AriaDto): Area {
        return with(area) {
            Area(area.id, area.name, area.url)
        }
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
