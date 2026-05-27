package ru.practicum.android.diploma.favorites.data.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_keyskill",
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
class FavoriteKeySkillEntity(
    val vacancyId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)
