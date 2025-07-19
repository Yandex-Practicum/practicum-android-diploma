package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.Debouncer

class VacancySearchUi(
    private val binding: VacancySearchFragmentBinding,
    private val adapter: VacancyItemAdapter,
    private val debouncer: Debouncer?,
    private val debounceForPlaceholder: Debouncer?,
    private val viewModel: VacanciesSearchViewModel,
    private val activity: Activity,
    private val context: Context,
    private val clearFocusView: View,
    private val vacancyList: MutableList<*>,
    private val onClear: () -> Unit
) {
    var isLoading: Boolean = false

    fun showLoadingMoreState() {
        isLoading = true
        adapter.addLoadingFooter()
    }

    fun showInitialState() {
        with(binding) {
            errorText.isVisible = false
            progressBar.isVisible = false
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = true
            searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
        }
    }

    fun showLoadingState() {
        with(binding) {
            errorText.isVisible = false
            progressBar.isVisible = true
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = false
        }
    }

    fun showSuccessState() {
        with(binding) {
            errorText.isVisible = false
            progressBar.isVisible = false
            searchMessage.isVisible = true
            recyclerViewSearch.isVisible = true
            searchMainPlaceholder.visibility = View.GONE
        }
        isLoading = false
        adapter.removeLoadingFooter()
    }

    fun showEmptyState() {
        with(binding) {
            progressBar.isVisible = false
            searchMessage.isVisible = true
            searchMessage.text = context.getString(R.string.no_vacancies)
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.isVisible = true
            errorText.isVisible = true
            errorText.text = context.getString(R.string.nothing_found)
            searchMainPlaceholder.setImageResource(R.drawable.nothing_found_placeholder)
        }
    }

    fun showNoInternetState() {
        with(binding) {
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.setImageResource(R.drawable.no_internet_placeholder)
        }
        adapter.removeLoadingFooter()
        debounceForPlaceholder?.submit {
            activity.runOnUiThread {
                with(binding) {
                    progressBar.isVisible = false
                    searchMainPlaceholder.isVisible = true
                    errorText.isVisible = true
                    errorText.text = context.getString(R.string.no_connection)
                }
            }
        }
    }

    fun showServerErrorState() {
        with(binding) {
            searchMessage.isVisible = false
            recyclerViewSearch.isVisible = false
            searchMainPlaceholder.setImageResource(R.drawable.server_error_placeholder)
        }
        debounceForPlaceholder?.submit {
            activity.runOnUiThread {
                with(binding) {
                    progressBar.isVisible = false
                    searchMainPlaceholder.isVisible = true
                    errorText.isVisible = true
                    errorText.text = context.getString(R.string.server_error)
                }
            }
        }
    }

    fun showNonEmptyInput() {
        with(binding) {
            icon.setImageResource(R.drawable.close_24px)
            searchMainPlaceholder.isVisible = false
            errorText.isVisible = false
            icon.isClickable = true

            icon.setOnClickListener {
                debouncer?.cancel()
                viewModel.resetState()
                adapter.submitList(emptyList())

                errorText.isVisible = false
                searchMessage.isVisible = false
                progressBar.isVisible = false
                recyclerViewSearch.isVisible = false

                inputEditText.clearFocus()
                inputEditText.setText("")
                searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)

                val inputMethodManager =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(clearFocusView.windowToken, 0)

                onClear()
            }
        }
    }

    fun showEmptyInput() {
        debouncer?.cancel()
        vacancyList.clear()

        with(binding) {
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
