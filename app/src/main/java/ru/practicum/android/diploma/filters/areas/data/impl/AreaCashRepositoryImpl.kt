package ru.practicum.android.diploma.filters.areas.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.search.data.impl.RequestBuilderRepositoryImpl.Companion.SAVED_AREA

class AreaCashRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : AreaCashRepository {
    private var areaForSave: Area? = getArea()

    override fun setCashArea(area: Area) {
        areaForSave = area
    }

    override fun cleanArea() {
        areaForSave = null
        saveAreaValueInSharedPrefs("")
    }

    override fun cleanCashRegion() {
        areaForSave = areaForSave?.copy(id = "", name = "")
    }

    override fun cleanCashArea() {
        areaForSave = null
    }

    override fun saveArea() {
        if (areaForSave != null) {
            saveAreaValueInSharedPrefs(gson.toJson(areaForSave))
        } else {
            saveAreaValueInSharedPrefs("")
        }
    }

    override fun getCashArea(): Area? {
        return areaForSave
    }

    override fun resetCashArea() {
        areaForSave = getArea()
    }

    private fun getArea(): Area? {
        val json = sharedPreferences.getString(SAVED_AREA, "")
        if (json.isNullOrEmpty()) return null
        return try {
            gson.fromJson(json, Area::class.java)

        } catch (e: JsonParseException) {
            null
        }
    }

    private fun saveAreaValueInSharedPrefs(value: String) {
        sharedPreferences.edit()
            .putString(SAVED_AREA, value)
            .apply()
    }
}
