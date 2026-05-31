package ru.practicum.android.diploma

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import ru.practicum.android.diploma.common.BaseUiTest

@RunWith(AndroidJUnit4::class)
class SearchScreenUiTest: BaseUiTest() {

    @Test
    fun searchScreenUiElementsTest() =
        run {
            with(screens) {
                with(searchScreen) {
                    step("Text field is diaplsyed") {
                        textField.assertIsDisplayed()
                    }
                    step("Vacancies list is displayed") {
                        vacanciesList.assertIsDisplayed()
                    }
                }
            }
        }

    @Test
    fun searchScreenVacancyClearTextEdit() =
        run {
            with(screens) {
                with(searchScreen) {
                    step("Enter text in search field") {
                        textField.performClick()
                        textField.performTextInput("Android")
                    }
                    step("Clear text in search field") {
                        clearButton.assertIsDisplayed()
                        clearButton.performClick()
                    }
                    step("Search field is empty") {
                        textField.assertTextEquals("")
                    }
                }
            }
        }
}
