package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.utils.VacanciesData.VacanciesSearchError
import ru.practicum.android.diploma.search.ui.adapter.VacanciesAdapter
import ru.practicum.android.diploma.vacancy.ui.VacancyFragment

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private val vacanciesAdapter: VacanciesAdapter by lazy {
        VacanciesAdapter { vacancy ->
            findNavController().navigate(
                R.id.action_searchFragment_to_vacancyFragment,
                VacancyFragment.createArguments(vacancy.id)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObservers()
        initializeAdapter()
        initializeScroll()
        initializeOther()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is SearchState.Content -> showContent(screenState)
                is SearchState.Error -> showError(screenState.error)
                is SearchState.Loading -> showLoading(screenState.isNewPage)
                is SearchState.Empty -> showEmpty()
            }
        }
    }

    private fun initializeAdapter() {
        binding.rvVacancies.adapter = vacanciesAdapter
    }

    private fun initializeScroll() {
        binding.rvVacancies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.rvVacancies.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = vacanciesAdapter.itemCount
                    if (pos >= itemsCount - ITEM_COUNT_TO_GET_NEW_PAGE) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
    }

    private fun initializeOther() {
        with(binding) {
            ivFilter.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
            }

            etSearch.doOnTextChanged { text, _, _, _ ->
                viewModel.search(text.toString())

                ivClear.isVisible = !text.isNullOrEmpty()
                ivSearch.isVisible = text.isNullOrEmpty()
                if (text.isNullOrEmpty()) {
                    showPlaceholder(R.drawable.placeholder_search)
                }
            }

            ivClear.setOnClickListener {
                etSearch.text.clear()
            }
        }
    }

    private fun showContent(screenState: SearchState.Content) {
        with(binding) {
            val oldCount = vacanciesAdapter.itemCount
            vacanciesAdapter.setItems(screenState.results)
            rvVacancies.isVisible = true
            numberVacancies.isVisible = true
            if (screenState.foundVacancies == 0) {
                numberVacancies.text = resources.getString(R.string.search_no_vacancies)
                showPlaceholder(R.drawable.placeholder_no_results_cat, R.string.search_no_results)
            } else {
                numberVacancies.text = getVacanciesText(requireContext(), screenState.foundVacancies)
                hidePlaceholder()
                tvPlaceholder.isVisible = false
            }
            progressBar.isVisible = false
            pbPagination.isVisible = false
            rvVacancies.setPadding(0, 0, 0, 0)

            if (oldCount > 0 && screenState.foundVacancies > 0) {
                rvVacancies.scrollToPosition(oldCount)
            }
        }
    }

    private fun showError(error: VacanciesSearchError) {
        binding.progressBar.isVisible = false

        when (error) {
            VacanciesSearchError.NO_INTERNET ->
                showPlaceholder(R.drawable.placeholder_no_internet, R.string.search_no_internet)

            else ->
                showPlaceholder(R.drawable.placeholder_server_error_cat, R.string.search_server_error)
        }
    }

    private fun showEmpty() {
        vacanciesAdapter.clearItems()
        binding.numberVacancies.isVisible = false
        binding.progressBar.isVisible = false

        showPlaceholder(R.drawable.placeholder_search)
    }

    private fun showLoading(isNewPage: Boolean) {
        with(binding) {
            if (isNewPage) {
                pbPagination.isVisible = true
                rvVacancies.setPadding(0, 0, 0, resources.getDimensionPixelOffset(R.dimen.padding80))
            } else {
                progressBar.isVisible = true
            }
        }

        hidePlaceholder()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun getVacanciesText(context: Context, count: Int): String {
        return context.resources.getQuantityString(R.plurals.vacancies, count, count)
    }

    private fun showPlaceholder(@DrawableRes drawableResId: Int, @StringRes textResId: Int? = null) {
        with(binding.tvPlaceholder) {
            isVisible = true
            setCompoundDrawablesWithIntrinsicBounds(0, drawableResId, 0, 0)

            textResId?.let { setText(it) } ?: run { text = null }
        }
    }

    private fun hidePlaceholder() {
        binding.tvPlaceholder.isVisible = false
    }

    private companion object {
        const val ITEM_COUNT_TO_GET_NEW_PAGE = 2
    }
}
