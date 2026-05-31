package ru.practicum.android.diploma.common

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import org.junit.Rule
import ru.practicum.android.diploma.ui.root.RootActivity

open class BaseUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    protected val screens = ScreenProvider(composeRule)
}
