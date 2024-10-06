package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

        if (savedInstanceState != null) binding.searchLine.setText(inputTextValue)

        viewModel.getStateObserve().observe(viewLifecycleOwner) { state ->
            render(state)
        }

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

                if (inputTextValue.isNotEmpty()) {
                    viewModel.searchDebounce(
                        changedText = inputTextValue
                    )
                    vacancies.clear()
                } else {
                    vacancies.clear()
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // коммент костыль
            }
        }
        binding.searchLine.addTextChangedListener(searchTextWatcher)

        binding.searchLineCleaner.setOnClickListener {
            view.hideKeyboard()
            binding.searchLine.setText("")
            TODO("Дефолт экран вьюмодель")
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
    }

    private fun render(state: VacancySearchScreenState) {
        when (state) {
            VacancySearchScreenState.Loading -> showLoadingProgress()
            is VacancySearchScreenState.Content -> showVacancies(state)
            VacancySearchScreenState.EmptyScreen -> showEmptyScreen()
            VacancySearchScreenState.NetworkError -> showError()
            is VacancySearchScreenState.PaginationError -> showError()
            VacancySearchScreenState.PaginationLoading -> showError()
            VacancySearchScreenState.SearchError -> showError()
            VacancySearchScreenState.ServerError -> showError()
        }
    }

    private fun showLoadingProgress() {
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun showVacancies(state: VacancySearchScreenState) {
        val loadingVacancies = (state as VacancySearchScreenState.Content).vacancies
        vacancies.addAll(loadingVacancies)
        binding.defaultSearchPlaceholder.visibility = View.GONE
        binding.notConnectedPlaceholder.visibility = View.GONE
        binding.notFoundPlaceholder.visibility = View.GONE
        binding.serverErrorPlaceholder.visibility = View.GONE
        binding.progressCircular.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showEmptyScreen() {
        // коммент костыль
    }

    private fun showError() {
        // коммент костыль
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
