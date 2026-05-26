package ru.practicum.android.diploma.presentation.vacancy.model

import androidx.compose.ui.text.AnnotatedString

sealed interface VacancyDescriptionElement {
    data class SubHeader(val text: String) : VacancyDescriptionElement

    data class Paragraph(val text: AnnotatedString) : VacancyDescriptionElement

    data class Bullet(val text: AnnotatedString) : VacancyDescriptionElement
}
