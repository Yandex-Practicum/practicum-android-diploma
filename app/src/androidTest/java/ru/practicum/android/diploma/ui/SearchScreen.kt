package ru.practicum.android.diploma.ui

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import ru.practicum.android.diploma.ext.nodeWithTag
import ru.practicum.android.diploma.ui.search.screen.SearchScreenTestTags

class SearchScreen(
    private val semanticProvider: SemanticsNodeInteractionsProvider,
) : ComposeScreen<SearchScreen>(
    semanticsProvider = semanticProvider,
    viewBuilderAction = {
        hasTestTag(SearchScreenTestTags.Container)
    },
) {
    val textField = nodeWithTag(
        semanticProvider,
        SearchScreenTestTags.TextField
    )

    val clearButton = nodeWithTag(
        semanticProvider,
        SearchScreenTestTags.ClearButton
    )

    val vacanciesList = nodeWithTag(
        semanticProvider,
        SearchScreenTestTags.VacanciesList
    )
}
