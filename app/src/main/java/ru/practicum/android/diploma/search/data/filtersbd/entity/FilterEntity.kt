package ru.practicum.android.diploma.search.data.filtersbd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Этот класс пока в разработке, поля могут быть добавлены/изменены

@Entity(tableName = "filter_table")
data class FilterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
