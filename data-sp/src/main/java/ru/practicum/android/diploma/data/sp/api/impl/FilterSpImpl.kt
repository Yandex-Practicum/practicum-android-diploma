package ru.practicum.android.diploma.data.sp.api.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.PlaceDto

private const val PLACE_KEY_SP = "place_key_sp"
private const val PLACE_KEY_SP_BUFFER = "place_key_sp_buffer"
private const val BRANCH_OF_PROFESSION_KEY_SP = "branch_of_profession_key_sp"
private const val EXPECTED_SALARY_KEY_SP = "expected_salary_key_sp"
private const val DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP = "do_not_show_without_salary_key_sp"

class FilterSpImpl(
    private val filterSp: SharedPreferences,
    private val gson: Gson
) : FilterSp {

    override suspend fun clearDataFilter() {
        filterSp.edit()
            .clear()
            .apply()
    }

    override suspend fun clearIndustryFilter() {
        filterSp.edit().remove(BRANCH_OF_PROFESSION_KEY_SP).apply()
    }

    override suspend fun clearPlaceFilter() {
        filterSp.edit().remove(PLACE_KEY_SP).apply()
    }

    override suspend fun clearSalaryFilter() {
        filterSp.edit().remove(EXPECTED_SALARY_KEY_SP).apply()
    }

    override suspend fun getPlaceDataFilter(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP)
    }

    override suspend fun getPlaceDataFilterBuffer(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP_BUFFER)
    }

    private suspend fun getPlaceDataFilterBase(key: String): PlaceDto? {
        val json = filterSp.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, PlaceDto::class.java)
        } else {
            null
        }
    }

    override suspend fun getBranchOfProfessionDataFilter(): String? {
        return filterSp.getString(BRANCH_OF_PROFESSION_KEY_SP, null)
    }

    @Suppress("detekt.SwallowedException")
    override suspend fun getExpectedSalaryDataFilter(): String? {
        var result: String? = null
        try {
            result = filterSp.getString(EXPECTED_SALARY_KEY_SP, null)
        } catch (e: ClassCastException) {
            result = null
        }
        return result
    }

    override suspend fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return filterSp.getBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP, false)
    }

    override suspend fun getDataFilter(): FilterDto {
        return FilterDto(
            placeDto = getPlaceDataFilter(),
            branchOfProfession = getBranchOfProfessionDataFilter(),
            expectedSalary = getExpectedSalaryDataFilter(),
            doNotShowWithoutSalary = isDoNotShowWithoutSalaryDataFilter()
        )
    }

    override suspend fun updatePlaceInDataFilter(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP)
    }

    override suspend fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP_BUFFER)
    }

    private suspend fun updatePlaceInDataFilterBase(placeDto: PlaceDto, key: String): Int {
        return withContext(Dispatchers.IO) {
            runCatching {
                val json = gson.toJson(placeDto)
                filterSp.edit()
                    .putString(key, json)
                    .apply()
            }.fold(
                onSuccess = { 1 },
                onFailure = { -1 }
            )
        }
    }

    override suspend fun updateProfessionInDataFilter(branchOfProfession: String?): Int {
        return withContext(Dispatchers.IO) {
            runCatching {
                filterSp.edit()
                    .putString(BRANCH_OF_PROFESSION_KEY_SP, branchOfProfession)
                    .apply()
            }.fold(
                onSuccess = { 1 },
                onFailure = { -1 }
            )
        }
    }

    override suspend fun updateSalaryInDataFilter(expectedSalary: String): Int {
        return withContext(Dispatchers.IO) {
            runCatching {
                filterSp.edit()
                    .putString(EXPECTED_SALARY_KEY_SP, expectedSalary)
                    .apply()
            }.fold(
                onSuccess = { 1 },
                onFailure = { -1 }
            )
        }
    }

    override suspend fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return withContext(Dispatchers.IO) {
            runCatching {
                filterSp.edit()
                    .putBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP, doNotShowWithoutSalary)
                    .apply()
            }.fold(
                onSuccess = { 1 },
                onFailure = { -1 }
            )
        }
    }
}
