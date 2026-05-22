package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun BadgeItem(modifier: Modifier = Modifier, vacancyAmount: Int) {
    val text = if (vacancyAmount > 0) {
        " ${
            pluralStringResource(
                id = R.plurals.vacancy_found_count,
                count = vacancyAmount,
                vacancyAmount
            )
        }"
    } else {
        stringResource(R.string.no_vacancies)
    }

    Text(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        text = text,
        style = MaterialTheme.typography.labelMedium,
        textAlign = TextAlign.Center
    )
}

private const val VACANCY_AMOUNT = 395

@Preview
@Composable
private fun BadgeItemPreview() {
    AppTheme {
        BadgeItem(Modifier, VACANCY_AMOUNT)
    }
}
