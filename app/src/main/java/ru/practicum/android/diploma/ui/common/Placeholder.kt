package ru.practicum.android.diploma.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R

@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun PlaceholderPreview() {
    Placeholder(imageRes = R.drawable.ic_initial_placeholder_328)
}
