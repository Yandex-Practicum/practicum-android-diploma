package ru.practicum.android.diploma.vacancy.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.ui.theme.Dimens

@Composable
fun VacancyEmployment(
    employment: String?,
    schedule: String?,
    modifier: Modifier = Modifier
) {
    val parts = listOfNotNull(employment, schedule).filter { it.isNotBlank() }
    if (parts.isEmpty()) return
    Text(
        text = parts.joinToString(", "),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)
    )
}
