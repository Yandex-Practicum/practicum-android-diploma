package ru.practicum.android.diploma.data.dto.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.data.dto.Response

@Parcelize
 class RegionListDto(
    val id: String,
    val parent_id: String,
    val name: String,
    val areas: List<RegionListDto>
) : Parcelable, Response()

