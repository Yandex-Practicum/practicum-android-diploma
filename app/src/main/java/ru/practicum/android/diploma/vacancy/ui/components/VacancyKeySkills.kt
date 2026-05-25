package ru.practicum.android.diploma.vacancy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun VacancyKeySkills(
    skills: List<String>,
    modifier: Modifier = Modifier
) {
    if (skills.isEmpty()) return
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = stringResource(R.string.vacancy_key_skills),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding8))
        skills.forEach { skill ->
            Row {
                Text(
                    text = "• ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = skill,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
