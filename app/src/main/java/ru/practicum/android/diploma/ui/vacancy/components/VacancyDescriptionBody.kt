package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDescriptionElement
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.util.extentions.toVacancyDescriptionElements

@Composable
fun VacancyDescriptionBody(
    descriptionHtml: String,
    modifier: Modifier = Modifier,
) {
    val sectionTitle = stringResource(R.string.vacancy_description_title)
    val elements = remember(descriptionHtml, sectionTitle) {
        descriptionHtml.toVacancyDescriptionElements(sectionTitle)
    }

    if (elements.isEmpty()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.VacancyBlockSpacing),
    ) {
        elements.forEach { element ->
            when (element) {
                is VacancyDescriptionElement.SubHeader -> VacancyDescriptionSubHeader(text = element.text)
                is VacancyDescriptionElement.Paragraph -> VacancyDescriptionParagraph(text = element.text)
                is VacancyDescriptionElement.Bullet -> VacancyBulletAnnotatedItem(text = element.text)
            }
        }
    }
}

@Composable
private fun VacancyDescriptionSubHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
private fun VacancyDescriptionParagraph(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
    )
}
