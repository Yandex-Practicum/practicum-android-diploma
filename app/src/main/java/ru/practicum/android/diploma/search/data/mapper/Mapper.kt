package ru.practicum.android.diploma.search.data.mapper

import android.util.Log
import ru.practicum.android.diploma.search.data.dto.AddressDto
import ru.practicum.android.diploma.search.data.dto.ContactsDto
import ru.practicum.android.diploma.search.data.dto.EmployerDto
import ru.practicum.android.diploma.search.data.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.SalaryDto
import ru.practicum.android.diploma.search.data.dto.ScheduleDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.domain.model.Address
import ru.practicum.android.diploma.search.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Employment
import ru.practicum.android.diploma.search.domain.model.Experience
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Schedule
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class Mapper {
    fun fromDto(vacancyDetailDto: VacancyDetailDto): VacancyDetail =
        VacancyDetail(
            id = vacancyDetailDto.id,
            name = vacancyDetailDto.name,
            description = vacancyDetailDto.description,
            salary = fromDto(vacancyDetailDto.salary),
            address = fromDto(vacancyDetailDto.address),
            experience = fromDto(vacancyDetailDto.experience),
            schedule = fromDto(vacancyDetailDto.schedule),
            employment = fromDto(vacancyDetailDto.employment),
            contacts = fromDto(vacancyDetailDto.contacts),
            employer = fromDto(vacancyDetailDto.employer),
            area = fromDto(vacancyDetailDto.area),
            skills = vacancyDetailDto.skills,
            url = vacancyDetailDto.url,
            industry = fromDto(vacancyDetailDto.industry)
        )

    fun fromDto(salaryDto: SalaryDto?): Salary? = if (salaryDto == null) null else
        Salary(
            salaryDto.from,
            salaryDto.to,
            salaryDto.currency
        )

    fun fromDto(addressDto: AddressDto?): Address? = if (addressDto == null) null else
        Address(
            addressDto.city,
            addressDto.street,
            addressDto.building,
            addressDto.fullAddress
        )

    fun fromDto(experienceDto: ExperienceDto?): Experience? = if (experienceDto == null) null else
        Experience(
            experienceDto.id,
            experienceDto.name
        )

    fun fromDto(scheduleDto: ScheduleDto?): Schedule? = if (scheduleDto == null) null else
        Schedule(
            scheduleDto.id,
            scheduleDto.name
        )

    fun fromDto(employmentDto: EmploymentDto?): Employment? = if (employmentDto == null) null else
        Employment(
            employmentDto.id,
            employmentDto.name
        )

    fun fromDto(employerDto: EmployerDto): Employer =
        Employer(
            employerDto.id,
            employerDto.name,
            employerDto.logo
        )

    fun fromDto(contactsDto: ContactsDto?): Contacts? = if (contactsDto == null) null else
        Contacts(
            contactsDto.id,
            contactsDto.name,
            contactsDto.email,
            contactsDto.phone
        )

    fun fromDto(filterIndustryDto: FilterIndustryDto): FilterIndustry =
        FilterIndustry(
            filterIndustryDto.id,
            filterIndustryDto.name
        )

    fun fromDto(filterAreaDto: FilterAreaDto): FilterArea =
        FilterArea(
            filterAreaDto.id,
            filterAreaDto.name,
            filterAreaDto.parentId,
            filterAreaDto.areas.map { FilterArea(it.id, it.name, it.parentId, emptyList()) }
        )

    fun fromDto(vacancyResponseDto: VacancyResponseDto): VacancyResponse =
        VacancyResponse(
            vacancyResponseDto.found,
            vacancyResponseDto.pages,
            vacancyResponseDto.page,
            vacancyResponseDto.vacancies?.map {
                Log.d("Taaaag", it.toString())
                fromDto(it)
            } ?: emptyList()
        )
}
