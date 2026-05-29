package ru.practicum.android.diploma.search.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.Spacer

@Composable
fun EmptyResultPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.padding16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_core_stub_not_found),
            contentDescription = null
        )
        Spacer(height = Dimens.padding16)
        Text(
            text = stringResource(id = R.string.search_error_not_found),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
