package ru.practicum.android.diploma.ui.team.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.team.theme.TeamColors
import ru.practicum.android.diploma.ui.team.theme.TeamTypography

@Composable
fun TeamInfoCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TeamColors.InfoCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            text = stringResource(R.string.team_footer_tagline),
            style = TeamTypography.FooterTagline,
            color = TeamColors.PrimaryBlue,
        )
    }
}
