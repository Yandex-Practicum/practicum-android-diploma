package ru.practicum.android.diploma.search.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.navigate.observable.Navigate
import ru.practicum.android.diploma.navigate.state.NavigateEventState
import ru.practicum.android.diploma.search.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.adapter.VacancyListAdapter
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.state.VacancyListState

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L

internal class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    private val vacancyListViewModel: VacancyListViewModel by viewModel()
    private val navigate: Navigate<NavigateEventState> by inject()
    private var localVacancyList: ArrayList<Vacancy> = ArrayList()

    val debouncedSearch by lazy {
        debounce(
            delayMillis = DELAY_CLICK_VACANCY,
            coroutineScope = lifecycleScope,
            useLastParam = true,
            actionThenDelay = false,
            action = { param: String ->
                vacancyListViewModel.initialSearch(param)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded) {
            outState.putString(USER_INPUT, userInputReserve)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            userInputReserve = savedInstanceState.getString(USER_INPUT, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBarSetup()

        recyclerSetup()

        setFilterIcon()

        vacancyListViewModel.screenStateLiveData.observe(viewLifecycleOwner) { state: SearchScreenState ->
            updateUI(state)
        }

        vacancyListViewModel.currentResultsCountLiveData.observe(viewLifecycleOwner) { count ->
            binding.resultCountPopup.text = if (count > 0) {
                requireContext().resources.getQuantityString(
                    ru.practicum.android.diploma.ui.R.plurals.vacancies_found,
                    count,
                    count
                )
            } else {
                getString(ru.practicum.android.diploma.ui.R.string.search_screen_no_results_popup)
            }
        }

        vacancyListViewModel.vacancyListStateLiveData.observe(viewLifecycleOwner) { state ->
            if (state is VacancyListState.Content && binding.vacancyRecycler.adapter != null) {
                localVacancyList = ArrayList()
                localVacancyList = state.vacancies as ArrayList<Vacancy>
                (binding.vacancyRecycler.adapter as VacancyListAdapter).setVacancies(localVacancyList)
                binding.vacancyRecycler.adapter?.notifyDataSetChanged()
            }
        }

        binding.filter.setOnClickListener {
            navigate.navigateTo(NavigateEventState.ToFilter)
        }

        binding.vacancyRecycler.setOnClickListener {
            requireContext().closeKeyBoard(binding.searchBar)
        }
    }

    private fun setFilterIcon() {
        val filterOnDrawable = AppCompatResources.getDrawable(
            requireContext(),
            ru.practicum.android.diploma.ui.R.drawable.search_filter_on_state
        )
        if (vacancyListViewModel.checkFilterState()) binding.filter.setImageDrawable(filterOnDrawable)
    }

    private fun recyclerSetup() {
        val adapter = VacancyListAdapter({ vacancy ->
            navigate.navigateTo(NavigateEventState.ToVacancyDataSourceNetwork(vacancy.id))
        }, vacancyListViewModel)
        binding.vacancyRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.vacancyRecycler.adapter = adapter
        (binding.vacancyRecycler.adapter as VacancyListAdapter).setVacancies(localVacancyList)

        binding.vacancyRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == localVacancyList.size - 1) {
                    vacancyListViewModel.loadNextPageRequest()
                }
            }
        })
    }

    private fun searchBarSetup() {
        binding.searchBar.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true) {
                binding.clearSearchIcon.isVisible = true
                binding.searchBarLoupeIcon.isVisible = false
                localVacancyList = ArrayList()
                debouncedSearch(text.toString())
            } else {
                binding.clearSearchIcon.isVisible = false
                binding.searchBarLoupeIcon.isVisible = true
            }
            userInputReserve = text.toString()
        }

        binding.clearSearchIcon.setOnClickListener {
            binding.searchBar.text.clear()
            requireContext().closeKeyBoard(binding.searchBar)
            vacancyListViewModel.emptyList()
        }
    }

    private fun disableAllVariableViews() {
        binding.defaultIllustration.isVisible = false
        binding.resultCountPopup.isVisible = false
        binding.failedToFetchListErrorIllustration.isVisible = false
        binding.failedToFetchListErrorText.isVisible = false
        binding.noInternetErrorIllustration.isVisible = false
        binding.noInternetErrorText.isVisible = false
        binding.vacancyRecycler.isVisible = false
        binding.progressBarLoadingFromSearch.isVisible = false
        binding.progressBarLoadingNewPage.isVisible = false
        binding.serverErrorIllustration.isVisible = false
        binding.serverErrorText.isVisible = false
    }

    private fun updateUI(state: SearchScreenState) {
        disableAllVariableViews()
        when (state) {
            SearchScreenState.Idle -> {
                binding.defaultIllustration.isVisible = true
            }

            SearchScreenState.LoadingNewList -> {
                binding.progressBarLoadingFromSearch.isVisible = true
            }

            SearchScreenState.LoadingNewPage -> {
                binding.resultCountPopup.isVisible = true
                binding.vacancyRecycler.isVisible = true
                binding.progressBarLoadingNewPage.isVisible = true
            }

            SearchScreenState.VacancyListLoaded -> {
                binding.resultCountPopup.isVisible = true
                binding.vacancyRecycler.isVisible = true
            }

            else -> {
                showError(state as SearchScreenState.Error)
            }
        }
    }

    private fun showError(error: SearchScreenState.Error) {
        when (error) {
            SearchScreenState.Error.FailedToFetchVacanciesError -> {
                binding.resultCountPopup.isVisible = true
                binding.failedToFetchListErrorIllustration.isVisible = true
                binding.failedToFetchListErrorText.isVisible = true
            }

            SearchScreenState.Error.NewPageNoInternetError -> {
                binding.resultCountPopup.isVisible = true
                binding.vacancyRecycler.isVisible = true
                makeToast(getString(ru.practicum.android.diploma.ui.R.string.search_screen_toast_no_internet))
            }

            SearchScreenState.Error.NewPageServerError -> {
                binding.serverErrorIllustration.isVisible = true
                binding.serverErrorText.isVisible = true
                makeToast(getString(ru.practicum.android.diploma.ui.R.string.search_screen_toast_error))
            }

            SearchScreenState.Error.NoInternetError -> {
                binding.noInternetErrorIllustration.isVisible = true
                binding.noInternetErrorText.isVisible = true
            }

            SearchScreenState.Error.ServerError -> {
                binding.serverErrorIllustration.isVisible = true
                binding.serverErrorText.isVisible = true
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
