package ru.practicum.android.diploma.search.data.network.test

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class TrackDto(
    val trackId: Long,
    val trackName: String? = "",
    val artistName: String? = "",
    @SerializedName("trackTimeMillis") val trackTime: String? = "",
    @SerializedName("artworkUrl100") val image: String? = "",
    @SerializedName("collectionName") val album: String? = "",
    @SerializedName("releaseDate") val year: String? = "",
    @SerializedName("primaryGenreName") val genre: String? = "",
    val country: String? = "",
    val previewUrl: String? = ""
) {
    override fun equals(other: Any?): Boolean {
        return if (other !is TrackDto) {
            false
        } else {
            other.trackId == trackId
        }
    }

    override fun hashCode(): Int {
        return trackId.toInt()
    }
}