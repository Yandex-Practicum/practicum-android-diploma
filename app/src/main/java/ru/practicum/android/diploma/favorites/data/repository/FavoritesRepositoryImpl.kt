package ru.practicum.android.diploma.favorites.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.favorites.data.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.data.dto.FavoriteContactEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteKeySkillEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoritePhoneEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyWithSkillsAndContactsEntity
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.models.Contacts
import ru.practicum.android.diploma.vacancy.domain.models.Phone
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails
import kotlin.String

class FavoritesRepositoryImpl(val dao: FavoriteVacancyDao) : FavoritesRepository {
    override suspend fun add(details: VacancyDetails) {
        dao.insert(mappingEntity(details))
        dao.insertContact(mappingContact(details.contacts, details.id))
        dao.insertPhones(mappingPhones(details.contacts.phones, details.id))
        dao.insertSkills(mappingKeySkills(details.keySkills, details.id))
    }

    override suspend fun remove(id: String) {
        dao.delete(id)
    }

    override fun getAll(): Flow<List<Vacancy>> = flow {
        dao.getAll().collect { entities ->
            emit(entities.map { mappingVacancy(it) })
        }
    }

    override fun getById(id: String): Flow<VacancyDetails?> = flow {
        dao.getById(id).collect { entity ->
            if (entity != null) {
                emit(mappingVacancyDetails(entity))
            } else {
                emit(null)
            }
        }
    }

    override fun isFavorite(id: String): Flow<Boolean> {
        return dao.isFavorite(id)
    }

    private fun mappingKeySkills(model: List<String>, id: String): List<FavoriteKeySkillEntity> {
        return model.map {
            FavoriteKeySkillEntity(
                vacancyId = id,
                name = it
            )
        }
    }

    private fun mappingContact(model: Contacts, id: String): FavoriteContactEntity {
        return FavoriteContactEntity(
            vacancyId = id,
            name = model.name,
            email = model.email
        )

    }

    private fun mappingPhones(model: List<Phone>, id: String): List<FavoritePhoneEntity> {
        return model.map {
            FavoritePhoneEntity(
                vacancyId = id,
                country = it.country,
                city = it.city,
                number = it.number,
                comment = it.comment
            )
        }
    }
    private fun mappingEntity(model: VacancyDetails): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            id = model.id,
            name = model.name,
            employerName = model.employerName,
            employerLogoUrl = model.employerLogoUrl,
            city = model.city,
            address = model.address,
            salary = model.salary,
            experience = model.experience,
            schedule = model.schedule,
            employment = model.employment,
            description = model.description,
            alternateUrl = model.alternateUrl,
        )
    }

    private fun mappingVacancy(model: FavoriteVacancyEntity): Vacancy {
        return Vacancy(
            id = model.id,
            name = model.name,
            employerName = model.employerName,
            employerLogoUrl = model.employerLogoUrl,
            salary = model.salary,
        )
    }

    private fun mappingVacancyDetails(model: FavoriteVacancyWithSkillsAndContactsEntity): VacancyDetails {
        return VacancyDetails(
            id = model.vacancy.id,
            name = model.vacancy.name,
            employerName = model.vacancy.employerName,
            employerLogoUrl = model.vacancy.employerLogoUrl,
            city = model.vacancy.city,
            address = model.vacancy.address,
            salary = model.vacancy.salary,
            experience = model.vacancy.experience,
            schedule = model.vacancy.schedule,
            employment = model.vacancy.employment,
            description = model.vacancy.description,
            alternateUrl = model.vacancy.alternateUrl,
            keySkills = model.keySkills.map {
                it.name
            },
            contacts = Contacts(
                name = model.contacts.name,
                email = model.contacts.email,
                phones = model.phones.map {
                    Phone(
                        country = it.country,
                        city = it.city,
                        number = it.number,
                        comment = it.comment
                    )
                }
            )
        )
    }
}
