package ru.practicum.android.diploma.data.sp.api.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.dto.FilterDto
import ru.practicum.android.diploma.data.sp.dto.IndustryDto
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

    override fun clearDataFilter() {
        filterSp.edit()
            .clear()
            .apply()
    }

    override fun clearIndustryFilter() {
        filterSp.edit().remove(BRANCH_OF_PROFESSION_KEY_SP).apply()
    }

    override fun clearPlaceFilter() {
        filterSp.edit().remove(PLACE_KEY_SP).apply()
    }

    override fun clearSalaryFilter() {
        filterSp.edit().remove(EXPECTED_SALARY_KEY_SP).apply()
    }

    override fun getPlaceDataFilter(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP)
    }

    override fun getPlaceDataFilterBuffer(): PlaceDto? {
        return getPlaceDataFilterBase(PLACE_KEY_SP_BUFFER)
    }

    private fun getPlaceDataFilterBase(key: String): PlaceDto? {
        val json = filterSp.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, PlaceDto::class.java)
        } else {
            null
        }
    }

    override fun getBranchOfProfessionDataFilter(): IndustryDto? {
        val json = filterSp.getString(BRANCH_OF_PROFESSION_KEY_SP, null)
        return if (json != null) {
            gson.fromJson(json, IndustryDto::class.java)
        } else {
            null
        }
    }

    @Suppress("detekt.SwallowedException")
    override fun getExpectedSalaryDataFilter(): String? {
        var result: String? = null
        try {
            result = filterSp.getString(EXPECTED_SALARY_KEY_SP, null)
        } catch (e: ClassCastException) {
            result = null
        }
        return result
    }

    override fun isDoNotShowWithoutSalaryDataFilter(): Boolean {
        return filterSp.getBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP, false)
    }

    override fun getDataFilter(): FilterDto {
        return FilterDto(
            placeDto = getPlaceDataFilter(),
            branchOfProfession = getBranchOfProfessionDataFilter(),
            expectedSalary = getExpectedSalaryDataFilter(),
            doNotShowWithoutSalary = isDoNotShowWithoutSalaryDataFilter()
        )
    }

    override fun updatePlaceInDataFilter(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP)
    }

    override fun updatePlaceInDataFilterBuffer(placeDto: PlaceDto): Int {
        return updatePlaceInDataFilterBase(placeDto, PLACE_KEY_SP_BUFFER)
    }

    private fun updatePlaceInDataFilterBase(placeDto: PlaceDto, key: String): Int {
        return kotlin.runCatching {
            val json = gson.toJson(placeDto)
            filterSp.edit()
                .putString(key, json)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateProfessionInDataFilter(branchOfProfession: IndustryDto): Int {
        return kotlin.runCatching {
            val json = gson.toJson(branchOfProfession)
            filterSp.edit()
                .putString(BRANCH_OF_PROFESSION_KEY_SP, json)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateSalaryInDataFilter(expectedSalary: String): Int {
        return kotlin.runCatching {
            filterSp.edit()
                .putString(EXPECTED_SALARY_KEY_SP, expectedSalary)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }

    override fun updateDoNotShowWithoutSalaryInDataFilter(doNotShowWithoutSalary: Boolean): Int {
        return kotlin.runCatching {
            filterSp.edit()
                .putBoolean(DO_NOT_SHOW_WITHOUT_SALARY_KEY_SP, doNotShowWithoutSalary)
                .apply()
        }.fold(
            onSuccess = { 1 },
            onFailure = { -1 }
        )
    }
}
