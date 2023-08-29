package ru.practicum.android.diploma.search.data.network.test

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.Response


@Serializable
class TracksSearchResponse(@SerializedName("results") var results: ArrayList<TrackDto>): Response()