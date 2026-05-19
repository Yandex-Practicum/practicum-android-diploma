package ru.practicum.android.diploma.root.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.core.ui.navigation.NavGraph
import ru.practicum.android.diploma.core.ui.theme.AppTheme

@Composable
fun ComposeRoot() {
    val navController = rememberNavController()
    AppTheme {
        NavGraph(navController)
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ComposeRootPreview() {
    AppTheme {
        ComposeRoot()
    }
}
