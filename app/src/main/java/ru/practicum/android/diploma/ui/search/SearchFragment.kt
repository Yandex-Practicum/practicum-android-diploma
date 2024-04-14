package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.ui.details.DetailsFragment
import ru.practicum.android.diploma.ui.search.recycler.VacancyAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    private var searchJob: Job? = null

    private val inputMethodManager by lazy {
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private val onClick: (Vacancy?) -> Unit = {
        findNavController().navigate(
            R.id.action_searchFragment_to_fragmentDetails,
            bundleOf(DetailsFragment.vacancyIdKey to it?.id)
        )
    }

    private var vacancyAdapter: VacancyAdapter = VacancyAdapter(onClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindVacancyAdapter()

        bindKeyboardSearchButton()
        bindTextWatcher()
        bindCrossButton()

        binding.searchFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterAllFragment)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

    }

    override fun onResume() {
        super.onResume()
        bindFilterButtonIcon()
    }

    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Default -> showDefaultState()
            is SearchViewState.Content -> showContent(state.vacancies, state.found)
            is SearchViewState.Loading -> showLoading()
            is SearchViewState.NoInternet -> showNoInternetState()
            is SearchViewState.EmptyVacancies -> showEmptyVacanciesState()
        }
    }

    private fun bindFilterButtonIcon() = with(binding) {
        lifecycleScope.launch {
            viewModel.isExistFiltersFlow.collect {
                if (it) {
                    searchFilter.setImageResource(R.drawable.ic_filter_on_24px)
                } else {
                    searchFilter.setImageResource(R.drawable.ic_filter_off_24px)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showSearchInfo(found: Int) = with(binding) {
        if (found == -1) {
            tvSearchInfo.isVisible = false
        } else if (found == 0) {
            tvSearchInfo.isVisible = true
            tvSearchInfo.text = resources.getString(R.string.no_such_vacancies)
        } else {
            tvSearchInfo.isVisible = true
            tvSearchInfo.text = "Найдено ${
                resources.getQuantityString(
                    R.plurals.plurals_vacancies,
                    found,
                    found,
                )
            }"
        }
    }

    private fun makeSnackbar() {
        Snackbar.make(
            requireContext(),
            requireView(),
            resources.getString(R.string.check_internet_connection),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.retry) { /*vacancyAdapter.retry()*/ }.show()
    }

    private fun bindVacancyAdapter() {
        binding.rvVacancy.adapter =
            vacancyAdapter
    }

    private fun showDefaultState() = with(binding) {
        ivStartSearch.isVisible = true
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false
        tvSearchInfo.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    private fun showContent(vacancies: List<Vacancy>, found: Int) = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = true
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false
        tvSearchInfo.isVisible = true
        tvSearchInfo.text = "Найдено ${
            resources.getQuantityString(
                R.plurals.plurals_vacancies,
                found,
                found,
            )
        }"

        vacancyAdapter.submitList(vacancies)
    }

    private fun showLoading() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = true
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = false
        tvSearchInfo.isVisible = false
    }

    private fun showNoInternetState() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = true
        nothingFoundGroup.isVisible = false
        tvSearchInfo.isVisible = false
    }

    private fun showEmptyVacanciesState() = with(binding) {
        ivStartSearch.isVisible = false
        progressBar.isVisible = false
        rvVacancy.isVisible = false
        noInternetGroup.isVisible = false
        nothingFoundGroup.isVisible = true
        tvSearchInfo.isVisible = true
    }

    private fun bindKeyboardSearchButton() {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if ("" != binding.search.text.toString()) {
                    viewModel.lastQuery = binding.search.text.toString()

                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        viewModel.search(binding.search.text.toString())
                    }
                }
                true
            }
            false
        }
    }

    private fun bindTextWatcher() = with(binding) {
        search.addTextChangedListener(
            onTextChanged = { s, _, _, _ ->
                if (s.toString().isEmpty()) {
                    ivSearch.isVisible = true
                    ivCross.isVisible = false
                } else {
                    if (viewModel.lastQuery != search.text.toString()) {
                        Log.d("search", "${viewModel.lastQuery} != ${binding.search.text}")
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch {
                            delay(SEARCH_DEBOUNCE_DELAY)
                            viewModel.lastQuery = binding.search.text.toString()

                            viewModel.search(search.text.toString())
                        }
                    }

                    ivSearch.isVisible = false
                    ivCross.isVisible = true
                }
            }
        )
    }

    private fun bindCrossButton() = with(binding) {
        ivCross.setOnClickListener {
            search.setText("")

            showDefaultState()
            inputMethodManager?.hideSoftInputFromWindow(
                view?.windowToken,
                0
            )
            search.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchJob?.cancel()
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
