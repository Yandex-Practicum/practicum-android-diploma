package ru.practicum.android.diploma.search.presentation.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.search.R
import ru.practicum.android.diploma.search.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchScreenState

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    //private val searchFragmentViewModel: SearchFragmentViewModel by viewModel() //ждёт VM

    companion object {
        private const val USER_INPUT = "userInput"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBarSetUp()

        //searchFragmentViewModel.stateLiveData.observe(viewLifecycleOwner, Observer { //ждёт VM
        //    state -> updateUI(state)
        //})

        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }
        binding.vacancyRecycler.setOnClickListener { // по оформлению адаптера - заменить на клик по item
            findNavController().navigate(R.id.action_searchFragment_to_vacancy_navigation)
        }
    }

    private fun searchBarSetUp() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.searchBar.text.isNotEmpty()) {
                    //добавляем логику поиска + debounce
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.searchBar.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.clearSearchIcon.isVisible = true
                binding.searchBarLoupeIcon.isVisible = false
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

        }
    }

    private fun updateUI(state : SearchScreenState){
        when (state){
            SearchScreenState.FAILED_TO_FETCH_VACANCIES_ERROR -> {
                binding.resultCountPopup.isVisible=true
                binding.resultCountPopup.text= getString(ru.practicum.android.diploma.common_ui.R.string.search_screen_no_results_popup)
                binding.failedToFetchListErrorIllustration.isVisible=true
                binding.failedToFetchListErrorText.isVisible=true

                binding.noInternetErrorIllustration.isVisible=false
                binding.noInternetErrorText.isVisible=false
                binding.defaultIllustration.isVisible=false
                binding.vacancyRecycler.isVisible=false
                binding.progressBarLoadingFromSearch.isVisible=false
                binding.progressBarLoadingNewPage.isVisible=false
            }

            SearchScreenState.IDLE -> {
                binding.defaultIllustration.isVisible=true

                binding.resultCountPopup.isVisible=false
                binding.failedToFetchListErrorIllustration.isVisible=false
                binding.failedToFetchListErrorText.isVisible=false
                binding.noInternetErrorIllustration.isVisible=false
                binding.noInternetErrorText.isVisible=false
                binding.vacancyRecycler.isVisible=false
                binding.progressBarLoadingFromSearch.isVisible=false
                binding.progressBarLoadingNewPage.isVisible=false
            }

            SearchScreenState.LOADING_NEW_LIST -> {
                binding.progressBarLoadingFromSearch.isVisible=true

                binding.defaultIllustration.isVisible=false
                binding.resultCountPopup.isVisible=false
                binding.failedToFetchListErrorIllustration.isVisible=false
                binding.failedToFetchListErrorText.isVisible=false
                binding.noInternetErrorIllustration.isVisible=false
                binding.noInternetErrorText.isVisible=false
                binding.vacancyRecycler.isVisible=false
                binding.progressBarLoadingNewPage.isVisible=false
            }

            SearchScreenState.LOADING_NEW_PAGE -> {
                binding.resultCountPopup.isVisible=true
                //binding.resultCountPopup.text="" добавить по наличии логики
                binding.vacancyRecycler.isVisible=true
                binding.progressBarLoadingNewPage.isVisible=true

                binding.defaultIllustration.isVisible=false
                binding.failedToFetchListErrorIllustration.isVisible=false
                binding.failedToFetchListErrorText.isVisible=false
                binding.noInternetErrorIllustration.isVisible=false
                binding.noInternetErrorText.isVisible=false
                binding.progressBarLoadingFromSearch.isVisible=false
            }

            SearchScreenState.NO_INTERNET_ERROR -> {
                binding.noInternetErrorIllustration.isVisible=true
                binding.noInternetErrorText.isVisible=true

                binding.defaultIllustration.isVisible=false
                binding.resultCountPopup.isVisible=false
                binding.failedToFetchListErrorIllustration.isVisible=false
                binding.failedToFetchListErrorText.isVisible=false
                binding.vacancyRecycler.isVisible=false
                binding.progressBarLoadingFromSearch.isVisible=false
                binding.progressBarLoadingNewPage.isVisible=false
            }

            SearchScreenState.VACANCY_LIST_LOADED -> {
                binding.resultCountPopup.isVisible=true
                //binding.resultCountPopup.text="" добавить по наличии логики
                binding.vacancyRecycler.isVisible=true

                binding.defaultIllustration.isVisible=false
                binding.failedToFetchListErrorIllustration.isVisible=false
                binding.failedToFetchListErrorText.isVisible=false
                binding.noInternetErrorIllustration.isVisible=false
                binding.noInternetErrorText.isVisible=false
                binding.progressBarLoadingFromSearch.isVisible=false
                binding.progressBarLoadingNewPage.isVisible=false
            }
        }
    }

}
