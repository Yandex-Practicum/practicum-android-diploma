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
fun ErrorPlaceholder(isInternetError: Boolean) {
    val imageRes = if (isInternetError) R.drawable.image_core_stub_no_server_1 else R.drawable.image_core_stub_no_server_2
    val textRes = if (isInternetError) R.string.search_error_internet else R.string.search_error_server

    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.padding16),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = null)
        Spacer(height = Dimens.padding16)
        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
