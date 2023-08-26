package ru.practicum.android.diploma.filter.domain

interface SharedPrefsStorage {
    fun <T> writeData(key: String, data: T)
    fun <T> readData(key: String, defaultValue: T): T
}