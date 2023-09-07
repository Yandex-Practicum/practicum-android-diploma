package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.converter.DataConverter
import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.ui.models.SelectedData
import ru.practicum.android.diploma.util.thisName
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.read
import kotlin.concurrent.write

@Suppress("UNCHECKED_CAST")
class SharedPrefsStorageImpl @Inject constructor(
    private val converter: DataConverter,
    private val preferences: SharedPreferences,
    private val logger: Logger
) : LocalStorage {

    private val lock = ReentrantReadWriteLock()
    override fun <T> writeData(key: String, data: T) {
        logger.log(thisName, "writeData(key: $key, $data: T)")
        lock.write {
            when (data) {
                is Boolean -> preferences.edit().putBoolean(key, data).apply()
                is Int -> preferences.edit().putInt(key, data).apply()
                is String -> preferences.edit().putString(key, data).apply()
                else -> preferences.edit().putString(key, converter.dataToJson(data)).apply()
            }
        }
    }

    override fun <T> readData(key: String, defaultValue: DataType): T {
        logger.log(thisName, "readData(key: $key, $defaultValue: T): T")
        lock.read {
            return when (defaultValue) {
                DataType.BOOLEAN -> preferences.getBoolean(key, false) as T
                DataType.INT     -> preferences.getInt(key, 0) as T
                DataType.STRING  -> preferences.getString(key, "") as T
                DataType.OBJECT  -> preferences.getSelectedData(key) as T
            }
        }
    }

    private fun <T> SharedPreferences.getSelectedData(key: String): T {
        logger.log(thisName, "getSelectedData($key: String): T")
        return getString(key, null)
            ?.let { converter.dataFromJson(it, type<SelectedData>()) }
            ?: SelectedData() as T
    }

    private inline fun <reified T> type() = object: TypeToken<T>() {}.type

}

