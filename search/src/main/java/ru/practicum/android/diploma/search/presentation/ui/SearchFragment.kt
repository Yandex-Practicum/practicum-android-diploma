package ru.practicum.android.diploma.search.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.search.R
import ru.practicum.android.diploma.search.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.adapter.VacancyListAdapter
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListState
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel

private const val CONJUGATION_0 = 0
private const val CONJUGATION_1 = 1
private const val CONJUGATION_2 = 2
private const val CONJUGATION_4 = 4
private const val CONJUGATION_10 = 10
private const val CONJUGATION_11 = 11
private const val CONJUGATION_12 = 12
private const val CONJUGATION_14 = 14
private const val CONJUGATION_100 = 100

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    private val vacancyListViewModel: VacancyListViewModel by viewModel()
    private var localVacancyList: ArrayList<Vacancy> = ArrayList()

    companion object {
        private const val USER_INPUT = "userInput"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            updatePopupText(count)
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
        binding.vacancyRecycler.setOnClickListener { // по оформлении адаптера - заменить на клик по item
            findNavController().navigate(R.id.action_searchFragment_to_vacancy_navigation)
        }
    }

    private fun updatePopupText(count: Int) {
        val text = when {
            count == CONJUGATION_0 -> getString(R.string.search_screen_no_results_popup)
            count % CONJUGATION_10 == CONJUGATION_1 && count % CONJUGATION_100 != CONJUGATION_11 ->
                getString(R.string.search_screen_result_count_popup1, count)
            count % CONJUGATION_10 in CONJUGATION_2..CONJUGATION_4 &&
                count % CONJUGATION_100 !in CONJUGATION_12..CONJUGATION_14 ->
                getString(R.string.search_screen_result_count_popup2, count)
            else -> getString(R.string.search_screen_result_count_popup3, count)
        }
        binding.resultCountPopup.text = text
    }

    private fun recyclerSetup() {
        val adapter = VacancyListAdapter { // сюда нашу клик-логику
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
                // +debounce
                localVacancyList = ArrayList()

                vacancyListViewModel.initialSearch(text.toString())
            } else {
                binding.clearSearchIcon.isVisible = false
                binding.searchBarLoupeIcon.isVisible = true
            }
            userInputReserve = text.toString()
        }

        binding.clearSearchIcon.setOnClickListener {
            binding.searchBar.text.clear()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                binding.searchBar.windowToken,
                0
            )
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
    }

    private fun updateUI(state: SearchScreenState) {
        disableAllVariableViews()
        when (state) {
            SearchScreenState.FAILED_TO_FETCH_VACANCIES_ERROR -> {
                binding.resultCountPopup.isVisible = true
                binding.failedToFetchListErrorIllustration.isVisible = true
                binding.failedToFetchListErrorText.isVisible = true
            }

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

            SearchScreenState.NO_INTERNET_ERROR -> {
                binding.noInternetErrorIllustration.isVisible = true
                binding.noInternetErrorText.isVisible = true
            }

            SearchScreenState.VACANCY_LIST_LOADED -> {
                binding.resultCountPopup.isVisible = true
                binding.vacancyRecycler.isVisible = true
            }
        }
    }

    fun makeToast(text: String) {
        Toast.makeText(context, "text", Toast.LENGTH_SHORT).show()
    }

}
