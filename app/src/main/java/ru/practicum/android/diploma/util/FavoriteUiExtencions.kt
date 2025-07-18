package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.R

fun Boolean.toFavoriteIcon(): Int = when (this) {
    true -> R.drawable.favorites_icon
    false -> R.drawable.favorites_off_24
}
