package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.di.annotations.TestClass
import ru.practicum.android.diploma.search.data.network.test.TrackDto

data class Vacancy(
    val id: Long,
    val iconUri: String = "",
    val title: String = "",
    val company: String = "",
    val salary: String = "",
    val date : Long = 0L
)
@TestClass
fun TrackDto.toVacancy(): Vacancy{
    return with(this) {
        Vacancy(id = trackId,
            iconUri = image?:"",
            title = trackName?:"",
            company = artistName?:"",
            salary = year.toString()?:"",
            date = 0L
            )
    }
}
@TestClass
fun mapTracksToVacancies(tracks: List<TrackDto>): List<Vacancy>{
    return tracks.map { it.toVacancy() }
}


