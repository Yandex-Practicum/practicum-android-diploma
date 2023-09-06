package ru.practicum.android.diploma.filter.data.local_storage

import ru.practicum.android.diploma.filter.data.model.DataType

interface LocalStorage {
    fun <T> writeData(key: String, data: T)
    fun <T> readData(key: String, defaultValue: DataType): T
}