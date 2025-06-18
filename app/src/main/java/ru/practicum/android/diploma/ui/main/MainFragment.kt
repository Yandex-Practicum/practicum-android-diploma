package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.main.adapters.SearchResultsAdapter
import ru.practicum.android.diploma.ui.main.models.SearchContentStateVO
import ru.practicum.android.diploma.ui.main.utils.VacancyCallback
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment

class MainFragment : BindingFragment<FragmentMainBinding>() {

    private val viewModel by viewModel<MainViewModel>()

    private var vacanciesAdapter: SearchResultsAdapter? = null

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiToolbar()
        // системная кн назад
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )

        vacanciesAdapter = SearchResultsAdapter(
            clickListener = { vacancy ->
                (activity as RootActivity).setNavBarVisibility(false)
                findNavController().navigate(
                    R.id.action_mainFragment_to_vacancyFragment,
                    VacancyFragment.createArgs(vacancy.id)
                )
            },
        )

        viewModel.contentState.observe(viewLifecycleOwner) {
            renderContent(it)
        }

        viewModel.text.observe(viewLifecycleOwner) {
            val withClose = it.isNotEmpty()

            binding.searchEditText.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                requireContext().getDrawable(
                    if (withClose) R.drawable.close_24px else R.drawable.search_24px
                ),
                null
            )
        }

        viewModel.observeClearSearchInput().observe(viewLifecycleOwner) {
            binding.searchEditText.setText("")
        }

        initSearch()

        binding.searchResults.adapter = vacanciesAdapter
        binding.searchResults.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.searchResults.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemCount = vacanciesAdapter?.itemCount ?: 0
                    if (pos >= itemCount - 1) {
                        viewModel.doNextSearch()
                    }
                }
            }
        })
    }

    private fun initUiToolbar() {
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForSearchScreen()
        toolbar.setToolbarTitle(getString(R.string.vacancy_search))
        toolbar.setOnToolbarFilterClickListener {
            (activity as RootActivity).setNavBarVisibility(false)
            findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
        }
        /*
        * !!! после того, как настроены все фильтры
        * применить toolbar.setFilterState(true) для изменения иконки кнопки
        */

    }

    //  callback для системной кн назад - выход из приложения
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChange((s ?: "").toString())
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.searchEditText.setOnTouchListener { v, event ->
            handleSearchTouch(v, event)
        }
    }

    private fun handleSearchTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val textView = v as TextView
            if (event.x >= textView.width - textView.compoundPaddingEnd) {
                onSearchClear()
                return true
            }
        }
        return false
    }

    private fun onSearchClear() {
        binding.searchEditText.clearFocus()

        viewModel.onSearchClear()
    }

    private fun renderContent(state: SearchContentStateVO) {
        when (state) {
            is SearchContentStateVO.Base -> showBaseView()
            is SearchContentStateVO.Loading -> showLoadingState(state.firstSearch)
            is SearchContentStateVO.Error -> showErrorState(state.noInternet)
            is SearchContentStateVO.Success -> showSearchResults(state.vacancies, state.found)
        }
    }

    private fun showBaseView() {
        binding.searchBaseState.isVisible = true
        binding.progress.isVisible = false
        binding.progressPages.isVisible = false
        binding.noInternetError.isVisible = false
        binding.unknownError.isVisible = false
        binding.searchResults.isVisible = false
        binding.vacanciesCount.isVisible = false
    }

    private fun showLoadingState(firstSearch: Boolean) {
        binding.searchBaseState.isVisible = false
        binding.noInternetError.isVisible = false
        binding.unknownError.isVisible = false
        binding.searchResults.isVisible = true
        binding.progress.isVisible = firstSearch
        binding.progressPages.isVisible = !firstSearch
    }

    private fun showSearchResults(newVacancies: List<Vacancy>, found: Int) {
        binding.searchBaseState.isVisible = false
        binding.progress.isVisible = false
        binding.progressPages.isVisible = false
        binding.noInternetError.isVisible = false
        binding.unknownError.isVisible = false
        binding.vacanciesCount.isVisible = true

        vacanciesAdapter?.let {
            val diffVacanciesCallback = VacancyCallback(it.vacancies, newVacancies)
            val diffVacancies = DiffUtil.calculateDiff(diffVacanciesCallback)
            it.vacancies.clear()
            it.vacancies.addAll(newVacancies)
            diffVacancies.dispatchUpdatesTo(it)
        }

        binding.vacanciesCount.text = resources.getQuantityString(
            R.plurals.vacancies_found,
            found,
            found,
        )
        binding.searchResults.isVisible = found > 0
    }

    private fun showErrorState(isNoInternetError: Boolean) {
        binding.searchBaseState.isVisible = false
        binding.progress.isVisible = false
        binding.progressPages.isVisible = false
        binding.noInternetError.isVisible = isNoInternetError
        binding.unknownError.isVisible = !isNoInternetError
        binding.searchResults.isVisible = false
        binding.vacanciesCount.isVisible = false
    }
}
