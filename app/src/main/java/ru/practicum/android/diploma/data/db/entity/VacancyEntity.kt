package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.`object`.Area
import ru.practicum.android.diploma.domain.models.`object`.Contacts
import ru.practicum.android.diploma.domain.models.`object`.Employment
import ru.practicum.android.diploma.domain.models.`object`.Experience
import ru.practicum.android.diploma.domain.models.`object`.KeySkills
import ru.practicum.android.diploma.domain.models.`object`.Salary
import ru.practicum.android.diploma.domain.models.`object`.Schedule

@Entity(tableName = "favorite_table")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    // val area: Area?,
    // val contacts: Contacts?,
    val description: String,
    // val employment: Employment?,
    //  val experience: Experience?,
    //   val keySkills: ArrayList<KeySkills>,
    val name: String,
    //  val salary: Salary?,
    //  val schedule: Schedule?,
    val isFavorite: Boolean = false,
    val date: Long = 0L
)
