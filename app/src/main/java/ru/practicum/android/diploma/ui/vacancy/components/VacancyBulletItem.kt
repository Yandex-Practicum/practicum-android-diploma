package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyBulletItem(
    text: String,
    modifier: Modifier = Modifier,
) {
    VacancyBulletRow(
        text = AnnotatedString(text.trimLeadingListMarker()),
        modifier = modifier,
    )
}

@Composable
fun VacancyBulletAnnotatedItem(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    VacancyBulletRow(
        text = text.trimLeadingListMarker(),
        modifier = modifier,
    )
}

@Composable
private fun VacancyBulletRow(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    if (text.text.isBlank()) {
        return
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = Dimens.VacancyBulletStartPadding),
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.VacancyBulletTextGap),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

private fun String.trimLeadingListMarker(): String {
    return trimStart()
        .removePrefix("•")
        .removePrefix("·")
        .removePrefix("●")
        .removePrefix("-")
        .removePrefix("–")
        .trimStart()
}

private fun AnnotatedString.trimLeadingListMarker(): AnnotatedString {
    val trimmedText = text.trimLeadingListMarker()
    if (trimmedText == text) {
        return this
    }
    val removedChars = text.length - trimmedText.length
    return AnnotatedString(
        text = trimmedText,
        spanStyles = spanStyles.mapNotNull { range ->
            val start = range.start - removedChars
            val end = range.end - removedChars
            if (start >= 0 && end <= trimmedText.length && start < end) {
                AnnotatedString.Range(range.item, start, end)
            } else {
                null
            }
        },
    )
}
