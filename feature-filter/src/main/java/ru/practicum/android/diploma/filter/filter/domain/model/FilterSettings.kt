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

    @Suppress("detekt.DataClassContainsFunctions")
    fun resetPlaceSettings(): FilterSettings {
        return this.copy(placeSettings = PlaceSettings(null, null, null, null))
    }

    @Suppress("detekt.DataClassContainsFunctions")
    fun resetBranchOfProfession(): FilterSettings {
        return this.copy(branchOfProfession = IndustrySetting(null, null))
    }

    @Suppress("detekt.DataClassContainsFunctions")
    fun updateExpectedSalary(newSalary: String?): FilterSettings {
        return this.copy(expectedSalary = newSalary)
    }

    @Suppress("detekt.DataClassContainsFunctions")
    fun updateDoNotShowWithoutSalary(newDoNotShowWithoutSalary: Boolean): FilterSettings {
        return this.copy(doNotShowWithoutSalary = newDoNotShowWithoutSalary)
    }

    @Suppress("detekt.CyclomaticComplexMethod")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FilterSettings) return false

        if (placeSettings != null && !placeSettings.equals(other.placeSettings)) return false
        if (branchOfProfession != null && !branchOfProfession.equals(other.branchOfProfession)) return false
        if (expectedSalary != null && !expectedSalary.equals(other.expectedSalary)) return false
        if (doNotShowWithoutSalary != other.doNotShowWithoutSalary) return false

        return true
    }

    override fun hashCode(): Int {
        var result = placeSettings?.hashCode() ?: 0
        result = 31 * result + (branchOfProfession?.hashCode() ?: 0)
        result = 31 * result + (expectedSalary?.hashCode() ?: 0)
        result = 31 * result + doNotShowWithoutSalary.hashCode()
        return result
    }

}
