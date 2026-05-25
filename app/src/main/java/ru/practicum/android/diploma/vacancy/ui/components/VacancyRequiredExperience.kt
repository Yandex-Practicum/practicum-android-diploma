package ru.practicum.android.diploma.vacancy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens

@Composable
fun VacancyRequiredExperience(
    experience: String?,
    modifier: Modifier = Modifier
) {
    if (experience.isNullOrBlank()) return
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = stringResource(R.string.vacancy_required_experience),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding4))
        Text(
            text = experience,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
