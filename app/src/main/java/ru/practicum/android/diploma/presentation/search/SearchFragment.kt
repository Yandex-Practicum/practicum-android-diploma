package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.presentation.VacancyAdapter
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: error("Binding is not initialized")
    private val searchViewModel by viewModel<SearchViewModel>()
    private var debouncedSearch: ((String) -> Unit)? = null
    private var debouncedClick: ((VacancyShort) -> Unit)? = null
    private var searchQuery: String = ""
    private var isDebounceEnabled = true
    private val adapter: VacancyAdapter = VacancyAdapter(
        vacancyList = mutableListOf(),
        onItemClickListener = { vacancy ->
            if (isDebounceEnabled) {
                isDebounceEnabled = false
                navigateToVacancyScreen(vacancy)
                debouncedClick?.let { it(vacancy) }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            debounceFunc()
            editText()
            setupBindings()
            observeSearchState()
        }
    }

    private fun observeSearchState() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchState.collect() { state ->
                renderState(state)
                Log.d("SearchFragment", "$state")
            }
        }
    }

    private fun renderState(state: SearchState<List<VacancyShort>>) {
        when {
            state.isLoading -> binding.stateLayout.show(ViewState.LOADING)
            state.error != null -> binding.stateLayout.show(ViewState.ERROR, state.error)
            state.content.isNullOrEmpty() -> binding.stateLayout.show(ViewState.EMPTY)
            else -> {
                binding.stateLayout.show(ViewState.CONTENT)
                adapter.updateVacancies(state.content)
            }
        }
        binding.vacanciesFoundText.apply {
            text = state.resultText
            visibility = if (state.showResultText) View.VISIBLE else View.GONE
        }
    }

    private fun editText() {
        binding.searchField.doOnTextChanged { text, _, _, _ ->
            searchQuery = text.toString()
            binding.clearFieldButton.isVisible = searchQuery.isNotBlank()
            binding.searchImage.isVisible = searchQuery.isBlank()

            if (searchQuery.isBlank()) {
                searchViewModel.clearSearch()
                isDebounceEnabled = false
            } else {
                isDebounceEnabled = true
                debouncedSearch?.invoke(searchQuery)
            }
        }
    }

    private fun debounceFunc() {
        debouncedSearch = debounce(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true
        ) {
            if (isDebounceEnabled) {
                searchViewModel.searchVacancy(searchQuery)
                toggleKeyboard(binding.searchField, false)
            }
        }
        debouncedClick = debounce(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = true,
        ) {
            isDebounceEnabled = true
        }
    }

    private fun setupBindings() {
        binding.stateLayout.apply {
            setLoadingView(R.layout.placeholder_loading)
            setEmptyView(R.layout.placeholder_search_new)
            setErrorView(UiError.NoConnection::class.java, R.layout.placeholder_no_internet)
            setErrorView(UiError.BadRequest::class.java, R.layout.placeholder_no_vacancies_list)
            setErrorView(UiError.ServerError::class.java, R.layout.placeholder_server_error_search)
        }
        binding.recyclerView.adapter = adapter
        binding.searchField.post {
            binding.searchField.requestFocus()
            toggleKeyboard(binding.searchField, true)
        }
        binding.clearFieldButton.setOnClickListener {
            binding.searchField.text.clear()
            binding.searchField.post {
                binding.searchField.requestFocus()
                toggleKeyboard(binding.searchField, true)
            }
        }
        binding.searchField.setOnEditorActionListener { v, actionId, event ->
            val isActionSearch = actionId == EditorInfo.IME_ACTION_SEARCH
            val isActionDone = actionId == EditorInfo.IME_ACTION_DONE
            val isEnterKey = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            if (isActionSearch || isActionDone || isEnterKey) {
                isDebounceEnabled = false
                searchViewModel.searchVacancy(searchQuery)
                toggleKeyboard(v, false)
                true
            } else {
                false
            }
        }
        binding.toFiltersButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_main_to_navigation_filters)
        }
    }

    private fun toggleKeyboard(view: View, show: Boolean) {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            view.post {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }

        } else {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // TO DO использовать в Адаптере
    @Suppress("unused")
    private fun navigateToVacancyScreen(vacancy: VacancyShort) {
        val args = bundleOf("vacancyId" to vacancy.vacancyId)
        findNavController().navigate(R.id.action_navigation_main_to_navigation_vacancy, args)
    }

    override fun onResume() {
        super.onResume()
        if (searchQuery.isBlank()) {
            binding.searchField.requestFocus()
            toggleKeyboard(binding.searchField, true)
        } else {
            searchViewModel.searchVacancy(searchQuery)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
