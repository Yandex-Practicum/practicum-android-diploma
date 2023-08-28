package ru.practicum.android.diploma.search.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacancy(
    val id: Int = 0,
    val iconUri: String = "",
    val title: String = "Android ждун",
    val company: String = "Google",
    val salary: String = "300 000 р.",
    val area: String = "Владивосток"
) : Parcelable
