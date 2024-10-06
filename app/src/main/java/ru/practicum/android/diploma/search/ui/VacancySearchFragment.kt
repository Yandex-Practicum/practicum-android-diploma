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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.search.presentation.VacancySearchScreenState
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel
import ru.practicum.android.diploma.search.ui.presenter.RecycleViewAdapter
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.hideKeyboard

class VacancySearchFragment : Fragment() {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private var vacancies = mutableListOf<VacancySearch>()
    private val viewModel by viewModel<VacancySearchViewModel>()
    private var adapter: RecycleViewAdapter? = null
    private var inputTextValue = DEF_TEXT
    private var onVacancyClickDebounce: ((VacancySearch) -> Unit)? = null

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

        if (savedInstanceState != null) {
            binding.searchLine.setText(inputTextValue)
        }

        viewModel.getStateObserve().observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.getVacancyListObserve().observe(viewLifecycleOwner) {
            if (it != null) {
                vacancies.addAll(it)
                adapter!!.notifyDataSetChanged()
            }
        }
        viewModel.checkVacancyState()

        binding.searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showLoadingProgress()
                viewModel.searchDebounce(inputTextValue)
                true
            }
            false
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // коммент костыль
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s)

                inputTextValue = s.toString()

                viewModel.searchDebounce(
                    changedText = inputTextValue
                )
                vacancies.clear()
                adapter!!.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
                // коммент костыль
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

    private fun recyclerViewInit() {
        onVacancyClickDebounce = debounce<VacancySearch>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancySearch ->
            viewModel.onVacancyClick(vacancySearch)
        }

        adapter = RecycleViewAdapter(vacancies, onVacancyClickDebounce!!)
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
            is VacancySearchScreenState.PaginationError -> paginationError(state.message)
            VacancySearchScreenState.PaginationLoading -> paginationLoading()
            VacancySearchScreenState.SearchError -> showError(state)
            VacancySearchScreenState.ServerError -> showError(state)
        }
    }

    private fun paginationError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        binding.recyclerView.smoothScrollToPosition(vacancies.size - 1)
    }

    private fun showLoadingProgress() {
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showVacancies() {
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.nextPageProgressCircular.visibility = View.GONE
    }

    private fun showEmptyScreen() {
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
                binding.notConnectedPlaceholder.visibility = View.VISIBLE
                binding.notFoundPlaceholder.visibility = View.GONE
                binding.serverErrorPlaceholder.visibility = View.GONE
            }

            is VacancySearchScreenState.SearchError -> {
                binding.notConnectedPlaceholder.visibility = View.GONE
                binding.notFoundPlaceholder.visibility = View.VISIBLE
                binding.serverErrorPlaceholder.visibility = View.GONE
            }

            is VacancySearchScreenState.ServerError -> {
                binding.notConnectedPlaceholder.visibility = View.GONE
                binding.notFoundPlaceholder.visibility = View.GONE
                binding.serverErrorPlaceholder.visibility = View.VISIBLE
            }

            is VacancySearchScreenState.PaginationError -> {
                // коммент костыль
            }

            else -> {
                null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DEF_TEXT = ""
        const val CLICK_DEBOUNCE_DELAY = 2000L
    }
}
