package ru.practicum.android.diploma.core.data.mapper

import androidx.room.TypeConverter
import ru.practicum.android.diploma.core.data.network.dto.CompanyLogoUrlsDto
import ru.practicum.android.diploma.core.data.network.dto.DetailVacancyResponse
import ru.practicum.android.diploma.core.data.network.dto.ShortVacancyDto
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity

object VacancyMapper {
    fun mapToDomain(shortVacancyDto: ShortVacancyDto): ShortVacancy {
        return with(shortVacancyDto) {
            ShortVacancy(
                id = id,
                name = name,
                companyName = employerInfo?.companyName.orEmpty(),
                city = locationInfo?.city.orEmpty(),
                salaryFrom = salaryInfo?.from.orEmpty(),
                salaryTo = salaryInfo?.to.orEmpty(),
                currency = salaryInfo?.currency.orEmpty(),
                employerLogoUrl = getActualLogo(shortVacancyDto.employerInfo?.companyLogoUrls)
            )
        }
    }

    fun mapToDomain(detailVacancy: DetailVacancyResponse): DetailVacancy {
        return DetailVacancy(
            id = detailVacancy.id,
            name = detailVacancy.name,
            salaryFrom = detailVacancy.salaryInfo?.from.orEmpty(),
            salaryTo = detailVacancy.salaryInfo?.to.orEmpty(),
            currency = detailVacancy.salaryInfo?.currency.orEmpty(),
            experience = detailVacancy.experience?.name.orEmpty(),
            employment = detailVacancy.employment?.name.orEmpty(),
            workSchedule = detailVacancy.workScheduleInfo?.name.orEmpty(),
            description = detailVacancy.description,
            keySkills = detailVacancy.keySkills,
            contactName = detailVacancy.contactInfo?.contactName.orEmpty(),
            email = detailVacancy.contactInfo?.email.orEmpty(),
            phone = detailVacancy.contactInfo?.phones?.firstOrNull()?.formatted.orEmpty(),
            contactComment = detailVacancy.contactInfo?.phones?.firstOrNull()?.comment.orEmpty(),
            employerLogoUrl = getActualLogo(detailVacancy.employerInfo?.companyLogoUrls),
            employerName = detailVacancy.employerInfo?.companyName.orEmpty(),
            city = detailVacancy.locationInfo?.city.orEmpty()
        )
    }

    fun mapToFavoritesEntity(detailVacancy: DetailVacancy): FavoriteEntity {
        return with(detailVacancy) {
            FavoriteEntity(
                id = id,
                name = name,
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                currency = currency,
                experience = experience,
                employment = employment,
                workSchedule = workSchedule,
                description = description,
                keySkills = keySkills,
                contactName = contactName,
                email = email,
                phone = phone,
                contactComment = contactComment,
                employerLogoUrl = employerLogoUrl,
                employerName = employerName,
                city = city
            )
        }
    }

    fun mapToDetailVacancy(favoriteEntity: FavoriteEntity): DetailVacancy {
        return with(favoriteEntity) {
            DetailVacancy(
                id = id,
                name = name,
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                currency = currency,
                experience = experience,
                employment = employment,
                workSchedule = workSchedule,
                description = description,
                keySkills = keySkills,
                contactName = contactName,
                email = email,
                phone = phone,
                contactComment = contactComment,
                employerLogoUrl = employerLogoUrl,
                employerName = employerName,
                city = city
            )
        }
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(separator = "\n")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split("\n")
    }

    private fun getActualLogo(companyLogoUrlsDto: CompanyLogoUrlsDto?): String {
        return if (companyLogoUrlsDto == null) {
            ""
        } else {
            when {
                companyLogoUrlsDto.small != null -> companyLogoUrlsDto.small
                companyLogoUrlsDto.medium != null -> companyLogoUrlsDto.medium
                else -> {
                    companyLogoUrlsDto.original
                }
            }
        }
    }
}
