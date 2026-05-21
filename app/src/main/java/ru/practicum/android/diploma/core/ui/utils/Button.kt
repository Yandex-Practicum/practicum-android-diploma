package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.theme.Red
import ru.practicum.android.diploma.core.ui.theme.WhiteUniversal

@Composable
fun Button(
    text: String,
    type: ButtonType = ButtonType.PRIMARY,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = buttonColors(
            containerColor = containerColor(type),
            contentColor = contentColor(type)
        ),
        shape = RoundedCornerShape(
            Dimens.radius12
        ),
        modifier = modifier
            .height(59.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
    }

}

enum class ButtonType {
    PRIMARY,
    TERTIARY

}

private fun containerColor(type: ButtonType): Color {
    return when (type) {
        ButtonType.PRIMARY -> Blue
        ButtonType.TERTIARY -> Color.Transparent
    }
}

private fun contentColor(type: ButtonType): Color {
    return when (type) {
        ButtonType.PRIMARY -> WhiteUniversal
        ButtonType.TERTIARY -> Red
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        Column(modifier = Modifier.padding(10.dp)) {
            Button("Some text") {}
            Button("Some other text", ButtonType.TERTIARY) {}
            Button("Some third text", modifier = Modifier.padding(horizontal = 40.dp)) {}
        }
    }

}
