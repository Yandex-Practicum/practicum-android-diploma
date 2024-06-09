package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.AreaDTO

data class CountriesResponse(val list: List<AreaDTO>) : Response()
