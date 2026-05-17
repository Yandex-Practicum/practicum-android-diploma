package ru.practicum.android.diploma.ui.root

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun ComposeRoot() {
    Text("Root")
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ComposeRootPreview() {
    AppTheme {
        ComposeRoot()
    }
}
