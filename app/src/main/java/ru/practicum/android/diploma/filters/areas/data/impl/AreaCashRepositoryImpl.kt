package ru.practicum.android.diploma.filters.areas.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.search.data.impl.RequestBuilderRepositoryImpl.Companion.SAVED_AREA
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class AreaCashRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val requestBuilderRepository: RequestBuilderRepository
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
        val bufSavedFilters = requestBuilderRepository.getBufferedSavedFilters()
        if (areaForSave != null) {
            requestBuilderRepository.updateBufferedSavedFilters(bufSavedFilters.copy(savedArea = areaForSave))
        } else {
            requestBuilderRepository.updateBufferedSavedFilters(bufSavedFilters.copy(savedArea = null))
        }
    }

    override fun getCashArea(): Area? {
        return areaForSave
    }

    override fun resetCashArea() {
        areaForSave = getArea()
    }

    private fun getArea(): Area? {
        return requestBuilderRepository.getBufferedSavedFilters().savedArea
    }

    private fun saveAreaValueInSharedPrefs(value: String) {
        sharedPreferences.edit()
            .putString(SAVED_AREA, value)
            .apply()
    }
}
