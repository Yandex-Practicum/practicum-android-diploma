package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.search.presentation.VacancySearchScreenState
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel
import ru.practicum.android.diploma.search.ui.presenter.RecycleViewAdapter
import ru.practicum.android.diploma.util.hideKeyboard
import ru.practicum.android.diploma.util.vacanciesPluralsFormat
import ru.practicum.android.diploma.vacancy.ui.VacancyDetailFragment

class VacancySearchFragment : Fragment() {

    private var _binding: VacancySearchFragmentBinding? = null
    private var _adapter: RecycleViewAdapter? = null
    private val binding get() = _binding!!
    private val adapter get() = _adapter!!
    private val viewModel by viewModel<VacancySearchViewModel>()
    private var inputTextValue = DEF_TEXT
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewInit()
        observeInit()
        viewModel.checkVacancyState()

        if (savedInstanceState != null) {
            binding.searchLine.setText(inputTextValue)
        }

        binding.searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showLoadingProgress()
                viewModel.searchDebounce(DEF_TEXT)
                viewModel.loadData(inputTextValue)
                true
            }
            false
        }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_filterSettingsFragment
            )
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // коммент для детекта
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s)

                inputTextValue = s.toString()

                viewModel.searchDebounce(
                    changedText = inputTextValue
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // коммент для детекта
            }
        }
        binding.searchLine.addTextChangedListener(searchTextWatcher)

        binding.searchLineCleaner.setOnClickListener {
            view.hideKeyboard()
            binding.searchLine.setText(DEF_TEXT)
            showEmptyScreen()
        }
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        val visibility = !s.isNullOrEmpty()
        binding.searchLineCleaner.isVisible = visibility
        binding.icSearch.isVisible = !visibility
    }

    private fun observeInit() {
        viewModel.getStateObserve().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun recyclerViewInit() {
        val onVacancyClickEvent = { vacancySearch: VacancySearch ->
            openVacancyDetails(vacancySearch.id)

        }
        _adapter = RecycleViewAdapter {
            onVacancyClickEvent(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recycleViewScrollListener()
    }

    private fun recycleViewScrollListener() {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    viewModel.nextPageDebounce(binding.searchLine.text.toString())
                }
            }
        }
        binding.recyclerView.addOnScrollListener(listener)
    }

    private fun render(state: VacancySearchScreenState) {
        when (state) {
            is VacancySearchScreenState.Loading -> showLoadingProgress()
            is VacancySearchScreenState.Content -> showVacancies(state.vacancies, state.vacanciesCount)
            is VacancySearchScreenState.EmptyScreen -> showEmptyScreen()
            is VacancySearchScreenState.NetworkError -> showError(state)
            is VacancySearchScreenState.PaginationError -> paginationError()
            is VacancySearchScreenState.PaginationLoading -> paginationLoading()
            is VacancySearchScreenState.SearchError -> showError(state)
            is VacancySearchScreenState.ServerError -> showError(state)
        }
    }

    private fun paginationError() {
        Toast.makeText(requireContext(), resources.getString(R.string.exeption), Toast.LENGTH_SHORT).show()
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun paginationLoading() {
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.VISIBLE

        if (adapter.listSize() > 0) {
            binding.recyclerView.smoothScrollToPosition(adapter.listSize() - 1)
        }
    }

    private fun showLoadingProgress() {
        adapter.notifyDataSetChanged()
        binding.recyclerView.visibility = View.GONE
        binding.blueTextView.visibility = View.GONE
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showVacancies(vacancies: List<VacancySearch>, vacanciesCount: Int) {
        view?.hideKeyboard()
        val stringBuilder = StringBuilder()
        stringBuilder
            .append("${resources.getString(R.string.found)} ")
            .append("$vacanciesCount ")
            .append(vacanciesPluralsFormat(vacancies.size, requireContext()))

        adapter.setList(vacancies)
        adapter.notifyDataSetChanged()

        binding.blueTextView.apply {
            text = stringBuilder
            visibility = View.VISIBLE
        }
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showEmptyScreen() {
        binding.blueTextView.visibility = View.GONE
        binding.defaultSearchPlaceholder.visibility = View.VISIBLE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showError(state: VacancySearchScreenState) {
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.nextPageProgressCircular.visibility = View.GONE

        when (state) {
            is VacancySearchScreenState.NetworkError -> {
                binding.blueTextView.visibility = View.GONE
                binding.notConnectedPlaceholder.visibility = View.VISIBLE
                binding.notFoundPlaceholder.visibility = View.GONE
                binding.serverErrorPlaceholder.visibility = View.GONE
            }

            is VacancySearchScreenState.SearchError -> {
                binding.blueTextView.apply {
                    text = resources.getString(R.string.not_found_blue_message)
                    visibility = View.VISIBLE
                }
                binding.notConnectedPlaceholder.visibility = View.GONE
                binding.notFoundPlaceholder.visibility = View.VISIBLE
                binding.serverErrorPlaceholder.visibility = View.GONE
            }

            is VacancySearchScreenState.ServerError -> {
                binding.blueTextView.visibility = View.GONE
                binding.notConnectedPlaceholder.visibility = View.GONE
                binding.notFoundPlaceholder.visibility = View.GONE
                binding.serverErrorPlaceholder.visibility = View.VISIBLE
            }

            else -> {
                null
            }
        }
    }

    private fun openVacancyDetails(vacancyId: String) {
        if (isClickAllowed) {
            findNavController().navigate(
                R.id.action_searchFragment_to_vacancyFragment,
                VacancyDetailFragment.createArgs(vacancyId)
            )
            isClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_TIME)
                isClickAllowed = true
            }
        }
    }

    private fun checkFilter() {
        if (viewModel.checkFilter()) {
            binding.filterButton.setImageResource(R.drawable.ic_filter_on_24px)
        } else {
            binding.filterButton.setImageResource(R.drawable.ic_filter_off_24px)
        }
    }

    override fun onResume() {
        super.onResume()
        checkFilter()
        viewModel.searchDebounce(DEF_TEXT)
        viewModel.loadData(inputTextValue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DEF_TEXT = ""
        const val CLICK_DEBOUNCE_TIME = 200L
    }
}
