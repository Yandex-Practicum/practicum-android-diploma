package ru.practicum.android.diploma.search.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.search.R
import ru.practicum.android.diploma.search.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.adapter.VacancyListAdapter
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListState
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel
import ru.practicum.android.diploma.vacancy.presentation.ui.VacancyFragment
import ru.practicum.android.diploma.vacancy.presentation.ui.state.VacancyInputState

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    private val vacancyListViewModel: VacancyListViewModel by viewModel()
    private var localVacancyList: ArrayList<Vacancy> = ArrayList()

    val debouncedSearch by lazy {
        debounce(
            delayMillis = DELAY_CLICK_VACANCY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
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
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

        binding.vacancyRecycler.setOnClickListener {
            requireContext().closeKeyBoard(binding.searchBar)
        }
    }

    private fun recyclerSetup() {
        val adapter = VacancyListAdapter { vacancy ->
            findNavController().navigate(
                R.id.action_searchFragment_to_vacancy_navigation,
                VacancyFragment.createArgs(VacancyInputState.VacancyNetwork(vacancy.id))
            )
        }
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
        binding.searchBar.doOnTextChanged { text, start, before, count ->
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
            SearchScreenState.IDLE -> {
                binding.defaultIllustration.isVisible = true
            }

            SearchScreenState.LOADING_NEW_LIST -> {
                binding.progressBarLoadingFromSearch.isVisible = true
            }

            SearchScreenState.LOADING_NEW_PAGE -> {
                binding.resultCountPopup.isVisible = true
                binding.vacancyRecycler.isVisible = true
                binding.progressBarLoadingNewPage.isVisible = true
            }

            SearchScreenState.VACANCY_LIST_LOADED -> {
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
            SearchScreenState.Error.FAILED_TO_FETCH_VACANCIES_ERROR -> {
                binding.resultCountPopup.isVisible = true
                binding.failedToFetchListErrorIllustration.isVisible = true
                binding.failedToFetchListErrorText.isVisible = true
            }

            SearchScreenState.Error.NEW_PAGE_NO_INTERNET_ERROR -> {
                binding.noInternetErrorIllustration.isVisible = true
                binding.noInternetErrorText.isVisible = true
                makeToast(getString(ru.practicum.android.diploma.ui.R.string.search_screen_toast_no_internet))
            }

            SearchScreenState.Error.NEW_PAGE_SERVER_ERROR -> {
                binding.serverErrorIllustration.isVisible = true
                binding.serverErrorText.isVisible = true
                makeToast(getString(ru.practicum.android.diploma.ui.R.string.search_screen_toast_error))
            }

            SearchScreenState.Error.NO_INTERNET_ERROR -> {
                binding.noInternetErrorIllustration.isVisible = true
                binding.noInternetErrorText.isVisible = true
            }

            SearchScreenState.Error.SERVER_ERROR -> {
                binding.serverErrorIllustration.isVisible = true
                binding.serverErrorText.isVisible = true
            }
        }
    }

    private fun makeToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
