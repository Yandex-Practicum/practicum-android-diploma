package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDetailContentUi
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyDetailsContent(
    content: VacancyDetailContentUi,
    modifier: Modifier = Modifier,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenHorizontalPadding)
            .padding(bottom = Dimens.ScreenContentBottomPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancySectionSpacing),
    ) {
        VacancyDetailsHeader(
            title = content.title,
            salaryText = content.salaryText,
        )
        VacancyCompanyBlock(
            companyName = content.companyName,
            logoUrl = content.logoUrl,
            locationText = content.locationText,
        )
        VacancyExperienceBlock(
            experience = content.experience,
            employmentAndSchedule = content.employmentAndSchedule,
        )
        if (content.descriptionHtml.isNotBlank()) {
            VacancyDescriptionBlock(descriptionHtml = content.descriptionHtml)
        }
        VacancySkillsBlock(skills = content.skills)
        VacancyContactsBlock(
            content = content,
            onEmailClick = onEmailClick,
            onPhoneClick = onPhoneClick,
        )
    }
}

@Composable
private fun VacancyDetailsHeader(
    title: String,
    salaryText: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancyBlockSpacing),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = salaryText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun VacancyExperienceBlock(
    experience: String?,
    employmentAndSchedule: String?,
) {
    if (experience.isNullOrBlank() && employmentAndSchedule.isNullOrBlank()) {
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancyBlockSpacing),
    ) {
        if (!experience.isNullOrBlank()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.VacancyTextSpacing),
            ) {
                Text(
                    text = stringResource(R.string.vacancy_required_experience),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = experience,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        if (!employmentAndSchedule.isNullOrBlank()) {
            Text(
                text = employmentAndSchedule,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun VacancyDescriptionBlock(
    descriptionHtml: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancyBlockSpacing),
    ) {
        Text(
            text = stringResource(R.string.vacancy_description_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        VacancyDescriptionBody(
            modifier = Modifier.fillMaxWidth(),
            descriptionHtml = descriptionHtml,
        )
    }
}
