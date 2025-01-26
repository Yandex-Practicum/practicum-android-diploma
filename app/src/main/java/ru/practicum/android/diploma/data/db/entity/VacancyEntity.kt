package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary

@Entity(tableName = "favorite_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: Long,
    val name: String?,
    val employer: Employer?,
    val salary: Salary?,
    var timeStamp: Long = System.currentTimeMillis()
)
