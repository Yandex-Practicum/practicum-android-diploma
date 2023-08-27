package ru.practicum.android.diploma.search.data.network.test

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.di.annotations.TestClass
import ru.practicum.android.diploma.search.data.network.Response

@TestClass
class TracksSearchResponse(@SerializedName("results") var results: ArrayList<TrackDto>): Response()