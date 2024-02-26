package ru.practicum.android.diploma.data.dto.favourites.room

object VacancyDetailsConverter {

    fun map(dto: VacancyDetailsDto): VacancyDetails =
        VacancyDetails(
            id = dto.id,
            url = dto.url,
            name = dto.name,
            area = dto.area.name.toString(),
            salaryCurrency = dto.salary?.currency,
            salaryFrom = dto.salary?.from,
            salaryTo = dto.salary?.to,
            salaryGross = dto.salary?.gross,
            experience = dto.experience?.name,
            schedule = dto.schedule.name,
            contactName = dto.contacts?.name,
            contactEmail = dto.contacts?.email,
            phones = dto.contacts?.phones?.map { phone ->
                "+${phone.country}${phone.city}${phone.number}"
            },
            contactComment = if (dto.contacts?.phones.isNullOrEmpty()) null else dto.contacts?.phones?.get(0)?.comment,
            logoUrl = dto.employer?.logoUrlsDto?.original,
            logoUrl90 = dto.employer?.logoUrlsDto?.art90,
            logoUrl240 = dto.employer?.logoUrlsDto?.art240,
            address = dto.address?.let { addressDto ->
                val builder = StringBuilder("")
                if (addressDto.city.isNotBlank()) builder.append(addressDto.city).append("")
                if (addressDto.street.isNotBlank()) builder.append(addressDto.street).append("")
                if (addressDto.building.isNotBlank()) builder.append(addressDto.building)
                if (builder.toString().isBlank()) null else builder.toString()
            },
            employerUrl = dto.employer?.alternateUrl,
            employerName = dto.employer?.name,
            employment = dto.employment?.name,
            keySkills = dto.keySkills?.map { skillDto ->
                skillDto.name
            },
            description = dto.description
        )

}
