package ru.practicum.android.diploma.search.data.network.test

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.practicum.android.diploma.search.data.network.CodeResponse


@Serializable
class TracksSearchCodeResponse(@SerializedName("results") var results: ArrayList<TrackDto>): CodeResponse()