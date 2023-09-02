package ru.practicum.android.diploma.features.search.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.features.search.presentation.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.App
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.search.presentation.SearchScreenState
import ru.practicum.android.diploma.features.search.presentation.SearchingCleanerState
import ru.practicum.android.diploma.features.vacancydetails.ui.VacancyDetailsFragment
import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel
import ru.practicum.android.diploma.root.presentation.ui.recyclerView.VacancyAdapter
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.hideKeyboard

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<SearchViewModel>()

    private val rwAdapter = VacancyAdapter(
        vacancyList = ArrayList(),
        onItemClickedAction = debounce(
            App.CLICK_DEBOUNCE_DELAY_MILLIS,
            lifecycleScope,
            false
        ) {vacancyId: String ->
            findNavController().navigate(R.id.action_searchFragment_to_vacancyDetailsFragment)
            VacancyDetailsFragment.createArgs(vacancyId)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureObservers()
        configureSearchingField()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun configureSearchingField() {
        val editField = binding?.searchEditField
        val searchingCleaner = binding?.searchingCleaner

        editField?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchingRequestChange(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        editField?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSearchingRequestChange(editField.text.toString())
                hideKeyboard(this@SearchFragment)
            }
            false
        }

        searchingCleaner?.setOnClickListener{
            editField?.setText("")
            viewModel.onSearchingFieldClean()
        }
    }

    private fun configureObservers() {
        viewModel.searchingCleanerStateObserve().observe(viewLifecycleOwner) { state ->
            updateSearchingCleanerState(state)
        }
        viewModel.searchScreenStateObserve().observe(viewLifecycleOwner) { state ->
            updateScreenState(state)
        }
        viewModel.chipMessageObserve().observe(viewLifecycleOwner) { message ->
            updateChip(message)
        }
    }

    private fun updateSearchingCleanerState(state: SearchingCleanerState) {
        binding?.searchingCleaner?.isVisible = state.isCleaner
        binding?.searchingCleanerPlaceholder?.isVisible = state.isPlaceholder
    }

    private fun updateScreenState(state: SearchScreenState) {
        binding?.chip?.isVisible = state.isChip
        binding?.vacancyFeed?.isVisible = state.isFeed
        binding?.progressBar?.isVisible = state.isProgressBar
        binding?.feedPlaceholder?.isVisible = state.isPlaceholder
    }

    private fun updateFeed(vacancyList: ArrayList<VacancyScreenModel>) {
        rwAdapter.updateItems(vacancyList)
    }

    private fun updateChip(text: String) {
        binding?.chip?.text = text
    }
}