package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo

data class ShortVacancyEntity(
    @ColumnInfo(name = "vacancy_id")
    val vacancyId: Long,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "employer")
    val employer: String?,
    @ColumnInfo(name = "salary")
    val salary: String?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long = System.currentTimeMillis()
)
