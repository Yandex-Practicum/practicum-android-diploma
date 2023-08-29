package ru.practicum.android.diploma.filter.data.local_storage

interface LocalStorage {
    fun <T> writeData(key: String, data: T)
    fun <T> readData(key: String, defaultValue: T): T
}