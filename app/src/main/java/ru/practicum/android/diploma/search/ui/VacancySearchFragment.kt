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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val binding get() = _binding!!
    private var vacancies = mutableListOf<VacancySearch>()
    private val viewModel by viewModel<VacancySearchViewModel>()
    private var adapter: RecycleViewAdapter? = null
    private var inputTextValue = DEF_TEXT
    private var onVacancyClickEvent: ((VacancySearch) -> Unit)? = null
    private var found = 0

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
        viewModel.getVacancyListObserve().observe(viewLifecycleOwner) {
            if (it != null) {
                vacancies.clear()
                vacancies.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
        viewModel.getVacancyClickEvent().observe(viewLifecycleOwner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }
        viewModel.getVacanciesSearchDataObserve().observe(viewLifecycleOwner) { data ->
            if (data != null) {
                found = data.found
            }
        }
    }

    private fun recyclerViewInit() {
        onVacancyClickEvent = { vacancySearch ->
            viewModel.onVacancyClick(vacancySearch)
        }

        adapter = RecycleViewAdapter(vacancies) {
            onVacancyClickEvent!!(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

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
            VacancySearchScreenState.Loading -> showLoadingProgress()
            is VacancySearchScreenState.Content -> showVacancies()
            VacancySearchScreenState.EmptyScreen -> showEmptyScreen()
            VacancySearchScreenState.NetworkError -> showError(state)
            is VacancySearchScreenState.PaginationError -> paginationError()
            VacancySearchScreenState.PaginationLoading -> paginationLoading()
            VacancySearchScreenState.SearchError -> showError(state)
            VacancySearchScreenState.ServerError -> showError(state)
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
        if (vacancies.size > 0) {
            binding.recyclerView.smoothScrollToPosition(vacancies.size - 1)
        }
    }

    private fun showLoadingProgress() {
        vacancies.clear()
        adapter?.notifyDataSetChanged()
        binding.blueTextView.visibility = View.GONE
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showVacancies() {
        view?.hideKeyboard()
        val stringBuilder =
            "${resources.getString(R.string.found)} " +
                "$found " +
                vacanciesPluralsFormat(vacancies.size, requireContext())

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
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
            VacancyDetailFragment.createArgs(vacancyId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DEF_TEXT = ""
    }
}
