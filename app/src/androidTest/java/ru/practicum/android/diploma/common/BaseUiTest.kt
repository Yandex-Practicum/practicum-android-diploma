package ru.practicum.android.diploma.common

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import ru.practicum.android.diploma.ui.root.RootActivity

open class BaseUiTest : TestCase() {

    @get:Rule
    val composeRule = createAndroidComposeRule<RootActivity>()

    protected val screens = ScreenProvider(composeRule)
}
