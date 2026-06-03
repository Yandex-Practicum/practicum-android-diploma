package ru.practicum.android.diploma.vacancy.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.LoadingContent
import ru.practicum.android.diploma.core.ui.utils.Stub
import ru.practicum.android.diploma.vacancy.ui.components.VacancyContacts
import ru.practicum.android.diploma.vacancy.ui.components.VacancyDescription
import ru.practicum.android.diploma.vacancy.ui.components.VacancyEmployment
import ru.practicum.android.diploma.vacancy.ui.components.VacancyHeader
import ru.practicum.android.diploma.vacancy.ui.components.VacancyKeySkills
import ru.practicum.android.diploma.vacancy.ui.components.VacancyRequiredExperience
import ru.practicum.android.diploma.vacancy.ui.mock.VacancyDetailsPreviewProvider

@Composable
fun VacancyScreen(viewModel: VacancyViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val content = state as? VacancyState.Content

    AppScreen(
        title = R.string.vacancy_screen_title,
        onBack = onBack,
        actions = {
            content?.let {
                ShareAction(it.details.alternateUrl)
                FavoriteAction(
                    isFavorite = it.isFavorite,
                    onClick = viewModel::onFavoriteClicked,
                )
            }
        }
    ) {
        when (val current = state) {
            VacancyState.Loading -> LoadingContent()
            is VacancyState.Content -> VacancyContent(current)
            VacancyState.NoInternet -> Stub(
                iconId = R.drawable.image_core_stub_no_internet,
                descriptionId = R.string.vacancy_error_no_internet,
            )
            VacancyState.NotFound -> Stub(
                iconId = R.drawable.image_vacancy_stub_not_found,
                descriptionId = R.string.vacancy_error_not_found,
            )
            VacancyState.Error -> Stub(
                iconId = R.drawable.image_vacancy_stub_no_server,
                descriptionId = R.string.vacancy_error_server,
            )
        }
    }
}

@Composable
private fun ShareAction(alternateUrl: String?) {
    if (alternateUrl.isNullOrBlank()) return
    val context = LocalContext.current
    IconButton(
        onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, alternateUrl)
            }
            context.startActivity(Intent.createChooser(shareIntent, null))
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_vacancy_share),
            contentDescription = stringResource(R.string.vacancy_share_description),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun FavoriteAction(isFavorite: Boolean, onClick: () -> Unit) {
    val iconRes = if (isFavorite) R.drawable.ic_vacancy_favorite_on else R.drawable.ic_vacancy_favorite_off
    val descriptionRes = if (isFavorite) R.string.vacancy_favorite_remove else R.string.vacancy_favorite_add
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = stringResource(descriptionRes),
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun VacancyContent(content: VacancyState.Content) {
    val details = content.details
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = Dimens.padding16)
    ) {
        VacancyHeader(
            name = details.name,
            salary = details.salary,
            employerName = details.employerName,
            employerLogoUrl = if (content.fromCache) null else details.employerLogoUrl,
            employerLocation = details.address ?: details.city ?: details.areaName
        )
        Spacer(Modifier.height(Dimens.padding24))
        VacancyRequiredExperience(experience = details.experience)
        Spacer(Modifier.height(Dimens.padding8))
        VacancyEmployment(employment = details.employment, schedule = details.schedule)
        Spacer(Modifier.height(Dimens.padding24))
        VacancyDescription(description = details.description)
        Spacer(Modifier.height(Dimens.padding24))
        VacancyKeySkills(skills = details.keySkills)
        Spacer(Modifier.height(Dimens.padding24))
        VacancyContacts(contacts = details.contacts)
        Spacer(Modifier.height(Dimens.padding24))
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VacancyScreenPreview(
    @PreviewParameter(VacancyDetailsPreviewProvider::class) model: VacancyViewModel
) {
    AppTheme {
        VacancyScreen(model, onBack = {})
    }
}
