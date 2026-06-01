package ru.practicum.android.diploma.vacancy.ui.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.vacancy.domain.models.Contacts

@Composable
fun VacancyContacts(
    contacts: Contacts?,
    modifier: Modifier = Modifier
) {
    if (contacts == null) return
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = stringResource(R.string.vacancy_contacts),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        contacts.name?.takeIf { it.isNotBlank() }?.let { name ->
            ContactRow(
                label = stringResource(R.string.vacancy_contact_name),
                value = name
            )
        }
        contacts.email?.takeIf { it.isNotBlank() }?.let { email ->
            ContactRow(
                label = stringResource(R.string.vacancy_contact_email),
                value = email,
                valueColor = MaterialTheme.colorScheme.primary,
                onValueClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_SENDTO).apply { data = "mailto:$email".toUri() }
                    )
                }
            )
        }
        if (contacts.phones.isNotEmpty()) {
            Spacer(Modifier.height(Dimens.padding12))
            Text(
                text = stringResource(R.string.vacancy_contact_phones),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            contacts.phones.forEach { phone ->
                Spacer(Modifier.height(Dimens.padding4))
                Text(
                    text = phone.number,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        context.startActivity(
                            Intent(Intent.ACTION_DIAL).apply { data = "tel:${phone.number}".toUri() }
                        )
                    }
                )
                phone.comment?.takeIf { it.isNotBlank() }?.let { comment ->
                    Text(
                        text = comment,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactRow(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground,
    onValueClick: (() -> Unit)? = null
) {
    Spacer(Modifier.height(Dimens.padding12))
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(Modifier.height(Dimens.padding4))
    val valueModifier = if (onValueClick != null) {
        Modifier.clickable { onValueClick() }
    } else {
        Modifier
    }
    Text(
        text = value,
        style = MaterialTheme.typography.bodyMedium,
        color = valueColor,
        modifier = valueModifier
    )
}
