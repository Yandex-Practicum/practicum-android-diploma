package ru.practicum.android.diploma

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import ru.practicum.android.diploma.common.BaseUiTest

@RunWith(AndroidJUnit4::class)
class SearchScreenUiTest: BaseUiTest() {

    @Test
    fun searchScreenUiElementsTest() {
        run {
            with(screens) {
                with(searchScreen) {
                    step("Text field is diaplsyed") {
                        textField.assertIsDisplayed()
                    }
                }
            }
        }
    }
}
