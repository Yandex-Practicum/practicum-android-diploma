package ru.practicum.android.diploma.favorites.data.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "favorite_vacancy")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val employerName: String,
    val employerLogoUrl: String,
    val city: String?,
    val address: String?,
    val salary: String,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String,
    val alternateUrl: String?,
)

data class FavoriteVacancyWithSkillsAndContactsEntity(
    @Embedded val vacancy: FavoriteVacancyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "vacancyId"
    )
    val keySkills: List<FavoriteKeySkillEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "vacancyId"
    )
    val contacts: FavoriteContactEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "vacancyId"
    )
    val phones: List<FavoritePhoneEntity>
)
