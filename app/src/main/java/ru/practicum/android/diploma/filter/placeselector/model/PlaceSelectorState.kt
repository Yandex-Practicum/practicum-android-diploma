package ru.practicum.android.diploma.filter.placeselector.model

import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.area.domain.model.Area

data class PlaceSelectorState(val country: Country? = null, val area: Area? = null)
