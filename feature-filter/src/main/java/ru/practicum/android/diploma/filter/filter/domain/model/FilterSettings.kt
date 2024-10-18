package ru.practicum.android.diploma.filter.filter.domain.model

data class FilterSettings(
    val placeSettings: PlaceSettings?,
    val branchOfProfession: IndustrySetting?,
    val expectedSalary: String?,
    val doNotShowWithoutSalary: Boolean,
) {
    companion object {
        fun emptyFilterSettings(): FilterSettings {
            return FilterSettings(
                placeSettings = PlaceSettings(null, null, null, null),
                branchOfProfession = IndustrySetting(null, null),
                expectedSalary = null,
                doNotShowWithoutSalary = false
            )
        }
    }
}

fun FilterSettings.resetPlaceSettings(): FilterSettings {
    return this.copy(placeSettings = PlaceSettings(null, null, null, null))
}

fun FilterSettings.resetBranchOfProfession(): FilterSettings {
    return this.copy(branchOfProfession = IndustrySetting(null, null))
}

fun FilterSettings.updateExpectedSalary(newSalary: String?): FilterSettings {
    return this.copy(expectedSalary = newSalary)
}

fun FilterSettings.updateDoNotShowWithoutSalary(newDoNotShowWithoutSalary: Boolean): FilterSettings {
    return this.copy(doNotShowWithoutSalary = newDoNotShowWithoutSalary)
}
