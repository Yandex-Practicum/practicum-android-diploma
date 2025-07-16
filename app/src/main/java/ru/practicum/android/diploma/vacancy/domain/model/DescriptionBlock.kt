package ru.practicum.android.diploma.vacancy.domain.model

sealed interface DescriptionBlock {
    data class Text(val html: String) : DescriptionBlock
    data class Image(val url: String, val caption: String? = null) : DescriptionBlock
}
