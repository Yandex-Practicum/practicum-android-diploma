package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.converter.DataConverter
import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.util.thisName
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

class SharedPrefsStorageImpl @Inject constructor(
    private val converter: DataConverter,
    private val preferences: SharedPreferences,
    private val logger: Logger
) : LocalStorage {

    private val lock = ReentrantReadWriteLock()
    override fun <T> writeData(key: String, data: T) {
        logger.log(thisName, "writeData(key: $key, data: T)")
        lock.write {
            when (data) {
                is Boolean -> preferences.edit().putBoolean(key, data).apply()
                is Int -> preferences.edit().putInt(key, data).apply()
                is String -> preferences.edit().putString(key, data).apply()
                else -> preferences.edit().putString(key, converter.dataToJson(data)).apply()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> readData(key: String, defaultValue: T): T {
        logger.log(thisName, "readData(key: $key, defaultValue: T): T")
        lock.read {
            return when (defaultValue) {
                is Boolean -> preferences.getBoolean(key, defaultValue as Boolean) as T
                is Int -> preferences.getInt(key, defaultValue as Int) as T
                is String -> preferences.getString(key, defaultValue) as T
                else -> preferences.getObject(key, defaultValue)
            }
        }
    }

    private fun <T> SharedPreferences.getObject(key: String, defaultValue: T): T {
        logger.log(thisName, "getObject(key: $key, defaultValue: T): T")
        val json = this.getString(key, null)
        return if (json == null || json == "null")
            defaultValue
        else
            converter.dataFromJson(json, defaultValue!!::class.java)
    }
}