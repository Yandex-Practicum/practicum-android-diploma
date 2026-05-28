package ru.practicum.android.diploma.favorites.data.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "favorite_contact",
    foreignKeys = [
        ForeignKey(
            entity = FavoriteVacancyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vacancyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["vacancyId"])]
)
class FavoriteContactEntity(
    val vacancyId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String?,
    val email: String?,
)

@Entity(
    tableName = "favorite_phone",
    foreignKeys = [
        ForeignKey(
            entity = FavoriteVacancyEntity::class,
            parentColumns = ["id"],
            childColumns = ["vacancyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["vacancyId"])]
)
class FavoritePhoneEntity(
    val vacancyId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: String?,
    val comment: String?
)

data class FavoriteContactWithPhonesEntity(
    @Embedded val contact: FavoriteVacancyEntity,
    @Relation(
        parentColumn = "vacancyId",
        entityColumn = "vacancyId"
    )
    val phones: List<FavoritePhoneEntity>,
)
