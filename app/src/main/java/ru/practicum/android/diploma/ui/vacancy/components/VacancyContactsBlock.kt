package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDetailContentUi
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyPhoneUi
import ru.practicum.android.diploma.ui.theme.Blue

@Composable
fun VacancyContactsBlock(
    content: VacancyDetailContentUi,
    modifier: Modifier = Modifier,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
) {
    if (!content.hasContacts()) {
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.vacancy_contacts_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        content.contactName?.let { name ->
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        content.email?.let { email ->
            Text(
                modifier = Modifier.clickable { onEmailClick(email) },
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = Blue,
            )
        }
        content.phones.forEach { phone ->
            VacancyPhoneItem(
                phone = phone,
                onPhoneClick = onPhoneClick,
            )
        }
    }
}

@Composable
private fun VacancyPhoneItem(
    phone: VacancyPhoneUi,
    onPhoneClick: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.clickable { onPhoneClick(phone.formatted) },
            text = phone.formatted,
            style = MaterialTheme.typography.bodyMedium,
            color = Blue,
        )
        phone.comment?.let { comment ->
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

private fun VacancyDetailContentUi.hasContacts(): Boolean {
    return !contactName.isNullOrBlank() ||
        !email.isNullOrBlank() ||
        phones.isNotEmpty()
}
