package ru.practicum.android.diploma.ui.search.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.fragment.VacancyFragment

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private val viewModel: SearchViewModel by viewModel()

    private var vacancyAdapter: VacancyAdapter? = null

    private var searchText: String = TEXT_VALUE

    private var searchJob: Job? = null
    private var clickJob: Job? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT, TEXT_VALUE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // восстанавливаем сохраненное значение
        if (savedInstanceState != null) {
            searchText = savedInstanceState.getString(
                INPUT_TEXT,
                TEXT_VALUE
            )
        }

        val owner = getViewLifecycleOwner()

        val onSearchTrackClick: (Vacancy) -> Unit =
            { vacancy: Vacancy -> viewModel.onVacancyClicked(vacancy) }
        vacancyAdapter = VacancyAdapter(onSearchTrackClick)
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearch.adapter = vacancyAdapter

        viewModel.getOpenMediaPlayerTrigger().observe(owner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }

        viewModel.searchResultLiveData()
            .observe(owner) { searchResult: SearchResult -> renderSearchResult(searchResult) }

        // нажатие на кнопку Done на клавиатуре
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editTextSearch.clearFocus()
                enterSearch()
            }
            false
        }

        // очистить строку поиска
        binding.clearIcon.setOnClickListener {
            binding.editTextSearch.setText("")
            viewModel.clearSearchResults()
            val view: View? = requireActivity().currentFocus

            if (view != null) {
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            binding.editTextSearch.clearFocus()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = clearButtonIsVisible(s)
                binding.searchIcon.isVisible = !clearButtonIsVisible(s)
                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = binding.editTextSearch.text.toString()
            }
        }
        binding.editTextSearch.addTextChangedListener(simpleTextWatcher)
    }

    // перейти на экран деаталей вакансии
    private fun openVacancyDetails(vacancyId: Long) {
        clickDebounce()
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment2,
            VacancyFragment.createArgs(vacancyId)
        )
    }

    private fun searchDebounce() {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            enterSearch()
        }
    }

    private fun clickDebounce() {
        clickJob?.cancel()
        clickJob = viewLifecycleOwner.lifecycleScope.launch {
            delay(CLICK_DEBOUNCE_DELAY)
        }
    }

    private fun renderSearchResult(result: SearchResult) {
        when (result) {
            is SearchResult.SearchVacanciesContent -> {
                binding.recyclerViewSearch.isVisible = true
                vacancyAdapter?.submitList(result.items)
            }
            is SearchResult.NoConnection -> {
                Toast.makeText(
                    requireContext(),
                    "Отсутствует подключение к интернету",
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {}
        }
    }

    private fun setMessage(text: String) {
        //todo
    }


    private fun enterSearch() {
        viewModel.searchVacancies(binding.editTextSearch.text.toString())
    }

    private fun clearButtonIsVisible(s: CharSequence?): Boolean {
        return !s.isNullOrEmpty()
    }

    companion object {
        const val INPUT_TEXT = "INPUT_TEXT"
        const val TEXT_VALUE = ""
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
