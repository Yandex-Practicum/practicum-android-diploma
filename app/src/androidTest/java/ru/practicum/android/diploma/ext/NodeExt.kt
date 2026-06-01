package ru.practicum.android.diploma.ext

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import io.github.kakaocup.compose.node.builder.NodeMatcher
import io.github.kakaocup.compose.node.core.BaseNode
import io.github.kakaocup.compose.node.element.KNode

fun BaseNode<*>.nodeWithTag(
    semanticsProvider: SemanticsNodeInteractionsProvider,
    testTag: String,
    useUnmergedTree: Boolean = true,
) = KNode(
    semanticsProvider = semanticsProvider,
    nodeMatcher = NodeMatcher(
        matcher = hasTestTag(testTag),
        useUnmergedTree = useUnmergedTree,
    ),
    parentNode = this,
)

@Suppress("SwallowedException", "ForEachOnRange")
fun KNode.scrollDownTo(
    semanticsProvider: SemanticsNodeInteractionsProvider,
    attempts: Int = 5,
    touchInput: () -> Unit = { semanticsProvider.onRoot().performTouchInput { swipeUp() } },
) {
    repeat(attempts.downTo(0).count()) {
        try {
            this.assertIsDisplayed()
            return
        } catch (e: AssertionError) {
            semanticsProvider.onRoot().performTouchInput { touchInput() }
        }
    }
}
