package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancySkillsBlock(
    skills: List<String>,
    modifier: Modifier = Modifier,
) {
    if (skills.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancyBlockSpacing),
    ) {
        Text(
            text = stringResource(R.string.vacancy_key_skills_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        skills.forEach { skill ->
            VacancyBulletItem(text = skill)
        }
    }
}
