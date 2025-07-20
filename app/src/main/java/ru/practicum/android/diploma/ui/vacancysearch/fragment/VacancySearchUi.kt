package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.Callbacks
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.StateHandlers
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.UiComponents

class VacancySearchUi(
    private val ui: UiComponents,
    private val state: StateHandlers,
    private val callbacks: Callbacks
) {
    var isLoading: Boolean = false

    fun showLoadingMoreState() {
        isLoading = true
        ui.adapter.addLoadingFooter()
    }

    fun showInitialState() {
        with(ui.binding) {
            errorText.isVisible = false
            progressBar.isVisible = false
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = true
            searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
        }
    }

    fun showLoadingState() {
        with(ui.binding) {
            errorText.isVisible = false
            progressBar.isVisible = true
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = false
        }
    }

    fun showSuccessState() {
        with(ui.binding) {
            errorText.isVisible = false
            progressBar.isVisible = false
            searchMessage.isVisible = true
            recyclerViewSearch.isVisible = true
            searchMainPlaceholder.visibility = View.GONE
        }
        isLoading = false
        ui.adapter.removeLoadingFooter()
    }

    fun showEmptyState() {
        with(ui.binding) {
            progressBar.isVisible = false
            searchMessage.isVisible = true
            searchMessage.text = ui.context.getString(R.string.no_vacancies)
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = true
            errorText.isVisible = true
            errorText.text = ui.context.getString(R.string.nothing_found)
            searchMainPlaceholder.setImageResource(R.drawable.nothing_found_placeholder)
        }
    }

    fun showNoInternetState() {
        with(ui.binding) {
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.setImageResource(R.drawable.no_internet_placeholder)
        }
        ui.adapter.removeLoadingFooter()
        state.debounceForPlaceholder?.submit {
            ui.activity.runOnUiThread {
                with(ui.binding) {
                    progressBar.isVisible = false
                    searchMainPlaceholder.isVisible = true
                    errorText.isVisible = true
                    errorText.text = ui.context.getString(R.string.no_connection)
                }
            }
        }
    }

    fun showServerErrorState() {
        with(ui.binding) {
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.setImageResource(R.drawable.server_error_placeholder)
        }
        state.debounceForPlaceholder?.submit {
            ui.activity.runOnUiThread {
                with(ui.binding) {
                    progressBar.isVisible = false
                    searchMainPlaceholder.isVisible = true
                    errorText.isVisible = true
                    errorText.text = ui.context.getString(R.string.server_error)
                }
            }
        }
    }

    fun showNonEmptyInput() {
        with(ui.binding) {
            icon.setImageResource(R.drawable.close_24px)
            searchMainPlaceholder.isVisible = false
            errorText.isVisible = false
            icon.isClickable = true

            icon.setOnClickListener {
                state.debouncer?.cancel()
                state.viewModel.resetState()
                ui.adapter.submitList(emptyList())

                errorText.isVisible = false
                searchMessage.isVisible = false
                progressBar.isVisible = false
                recyclerViewSearch.isVisible = false

                inputEditText.clearFocus()
                inputEditText.setText("")
                searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)

                val inputMethodManager =
                    ui.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(ui.clearFocusView.windowToken, 0)

                callbacks.onClear()
            }
        }
    }

    fun showEmptyInput() {
        state.debouncer?.cancel()
        state.vacancyList.clear()

        with(ui.binding) {
            searchMainPlaceholder.isVisible = true
            icon.setImageResource(R.drawable.search_24px)
            icon.isClickable = false
            progressBar.isVisible = false
            errorText.isVisible = false
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
        }
    }
}
