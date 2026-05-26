package ru.practicum.android.diploma.ui.vacancy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyCompanyBlock(
    companyName: String,
    logoUrl: String?,
    locationText: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.VacancyCardCornerRadius))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(Dimens.ScreenHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(Dimens.VacancyCompanyRowSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VacancyCompanyLogo(logoUrl = logoUrl)
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.VacancyTextSpacing),
        ) {
            Text(
                text = companyName.ifBlank { stringResource(R.string.no_company) },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            if (!locationText.isNullOrBlank()) {
                Text(
                    text = locationText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
