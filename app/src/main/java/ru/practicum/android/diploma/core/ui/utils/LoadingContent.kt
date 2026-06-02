package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens

@Composable
fun LoadingContent(modifier: Modifier = Modifier.fillMaxSize()) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            color = Blue

        )
    }
}

@Composable
fun SmallLoadingContent() {
    LoadingContent(
        modifier = Modifier.fillMaxWidth()
            .padding(Dimens.padding16)
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun LoadingContentMaxPreview() {
    AppTheme {
        LoadingContent()
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun LoadingContentSmallPreview() {
    AppTheme {
        LazyColumn {
            item {
                Text("Some content")
            }
            item {
                SmallLoadingContent()
            }
        }
    }
}
