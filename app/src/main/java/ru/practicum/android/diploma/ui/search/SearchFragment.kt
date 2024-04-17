package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.vacancies.response.ResponseCodes
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.ui.details.DetailsFragment
import ru.practicum.android.diploma.ui.search.recycler.VacancyAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchViewModel>()

    private var searchJob: Job? = null

    private var currentState: SearchViewState? = null

    private val inputMethodManager by lazy {
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    }

    private val onClick: (Vacancy?) -> Unit = {
        findNavController().navigate(
            R.id.action_searchFragment_to_fragmentDetails,
            bundleOf(
                DetailsFragment.vacancyIdKey to it?.id,
                DetailsFragment.vacancyNameKey to it?.contacts?.name,
                DetailsFragment.vacancyEmailKey to it?.contacts?.email,
                DetailsFragment.vacancyPhoneKey to it?.contacts?.phone,
                DetailsFragment.vacancyCommentKey to it?.contacts?.comment
                )
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
        bindAddOnScrollListener()

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
        currentState = state
        when (state) {
            is SearchViewState.Default -> showDefaultState()
            is SearchViewState.Content -> showContent(state.vacancies, state.found)
            is SearchViewState.Loading -> showLoading()
            is SearchViewState.NoInternet -> showNoInternetState()
            is SearchViewState.EmptyVacancies -> showEmptyVacanciesState()
            is SearchViewState.RecyclerLoading -> vacancyAdapter.addLoadingView()
            is SearchViewState.RecyclerError -> {
                vacancyAdapter.removeLoadingView()
                makeSnackbar(
                    if (state.errorMessage == ResponseCodes.NO_CONNECTION.code.toString()) {
                        getString(R.string.check_internet_connection)
                    } else {
                        getString(R.string.error_occurred)
                    }
                ).show()

            }
        }
    }

    private fun bindAddOnScrollListener() = with(binding) {
        rvVacancy.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (rvVacancy.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacancyAdapter.itemCount
                    if (pos >= itemsCount - 1) {
                        if (currentState is SearchViewState.RecyclerError) {
                            viewModel.setContentState()
                        }
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
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

    private fun makeSnackbar(errorMessage: String) =
        Snackbar.make(
            requireContext(),
            requireView(),
            errorMessage,
            Snackbar.LENGTH_SHORT
        ) // .setAction(R.string.retry) { viewModel.onLastItemReached() }

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
        tvSearchInfo.text =
            resources.getQuantityString(
                R.plurals.plurals_vacancies,
                found,
                found,
            )

        vacancyAdapter.updateVacancies(vacancies)
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
        tvSearchInfo.text = getString(R.string.no_such_vacancies)
    }

    private fun bindKeyboardSearchButton() {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    viewModel.clearPagingInfo()
                    viewModel.search(binding.search.text.toString())
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
                    searchJob?.cancel()
                } else {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        if (viewModel.lastQuery != s.toString()) {
                            delay(SEARCH_DEBOUNCE_DELAY)
                            viewModel.clearPagingInfo()
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
            vacancyAdapter.clearList()

            viewModel.setDefaultState()
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
