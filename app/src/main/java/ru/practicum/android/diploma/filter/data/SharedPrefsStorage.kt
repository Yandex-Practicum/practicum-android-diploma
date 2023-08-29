package ru.practicum.android.diploma.filter.data

interface SharedPrefsStorage {
    fun <T> writeData(key: String, data: T)
    fun <T> readData(key: String, defaultValue: T): T
}