package ru.practicum.android.diploma.common

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import ru.practicum.android.diploma.ui.SearchScreen

class ScreenProvider(
    semanticProvider: SemanticsNodeInteractionsProvider
) {
    val searchScreen = SearchScreen(semanticProvider)
}
