package ru.practicum.android.diploma.search.data.network.test



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TrackDto(
    val trackId: Long,
    val trackName: String? = "",
    val artistName: String? = "",
    @SerialName("trackTimeMillis") val trackTime: Long? = 0L,
    @SerialName("artworkUrl100") val image: String? = "",
    @SerialName("collectionName") val album: String? = "",
    @SerialName("releaseDate") val year: String? = "",
    @SerialName("primaryGenreName") val genre: String? = "",
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