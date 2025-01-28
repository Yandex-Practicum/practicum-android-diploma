package ru.practicum.android.diploma.ui.search.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.fragment.VacancyFragment
import ru.practicum.android.diploma.util.coroutine.CoroutineUtils

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }

    private val viewModel: SearchViewModel by viewModel()

    private var vacancyAdapter: VacancyAdapter? = null

    private var searchText: String = TEXT_VALUE

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

        val onSearchTrackClick: (Long) -> Unit =
            { vacancyId: Long -> viewModel.onVacancyClicked(vacancyId) }
        vacancyAdapter = VacancyAdapter(onSearchTrackClick)
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSearch.adapter = vacancyAdapter

        viewModel.getVacancyTrigger().observe(owner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }

        viewModel.searchResultLiveData()
            .observe(owner) { searchResult: SearchResult -> renderSearchResult(searchResult) }

        // нажатие на кнопку Done на клавиатуре просто скрывает ее
        binding.editTextSearch.setOnEditorActionListener { _, _, _ ->
            false
        }

        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.recyclerViewSearch.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter?.itemCount
                    if (itemsCount != null) {
                        if (pos >= itemsCount - 1) {
                            viewModel.onLastItemReached(binding.editTextSearch.text.toString())
                        }
                    }
                }
            }
        })

        // очистить строку поиска
        binding.clearIcon.setOnClickListener {
            clearSearchText()
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = clearButtonIsVisible(s)
                binding.searchIcon.isVisible = !clearButtonIsVisible(s)
                if (!s.isNullOrEmpty()) {
                    CoroutineUtils.debounce(lifecycleScope, SEARCH_DEBOUNCE_DELAY, { enterSearch() })
                }
            }

            override fun afterTextChanged(s: Editable?) {
                searchText = binding.editTextSearch.text.toString()
                if (s.isNullOrEmpty()) {
                    CoroutineUtils.debounceJob?.cancel()
                }
            }
        }
        binding.editTextSearch.addTextChangedListener(simpleTextWatcher)
    }

    private fun clearSearchText() {
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

    // перейти на экран деаталей вакансии
    private fun openVacancyDetails(vacancyId: Long) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment2,
            VacancyFragment.createArgs(vacancyId)
        )
    }

    private fun renderSearchResult(result: SearchResult) {
        val textView = binding.textViewVacancies
        when (result) {
            is SearchResult.SearchVacanciesContent -> {
                binding.progressBarSearch.isVisible = false
                binding.recyclerViewSearch.isVisible = true
                vacancyAdapter?.submitList(result.items)
                val searchedText = resources.getString(R.string.searched_text)
                val vacanciesText = resources.getString(R.string.vacancies_text)
                val text = "$searchedText ${result.items.size} $vacanciesText"
                textView.text = String.format(text)
                textView.isVisible = true

            }
            is SearchResult.NoConnection -> {
                Toast.makeText(
                    requireContext(),
                    "Отсутствует подключение к интернету",
                    Toast.LENGTH_LONG
                ).show()
            }
            is SearchResult.Loading -> {
                binding.progressBarSearch.isVisible = true
            }

            else -> {}
        }
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
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
