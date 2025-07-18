package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.presentation.mappers.toUiModel
import ru.practicum.android.diploma.presentation.models.vacancies.VacanciesState
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.TopSpacingItemDecoration
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.dpToPx

class VacancySearchFragment : Fragment(), VacancyItemAdapter.Listener {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModel<VacanciesSearchViewModel>()

    private var vacanciesList = ArrayList<VacancyUiModel>()
    private val adapter = VacancyItemAdapter(this)

    private var debouncer: Debouncer? = null

    private var debounceForPlaceholder: Debouncer? = null

    private var isLoading = false
    private var hasMore = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        debouncer = Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_DEBOUNCE_DELAY)
        debounceForPlaceholder = Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_ERROR_DELAY)

        initUI()
        observeViewModel()
        setupSearchInput()
        setupRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SEARCH_ERROR_DELAY = 700L
        private const val RECYCLER_MARGIN_TOP = 38F
        private const val TOTAL_COUNT = 20
    }

    override fun onClick(id: String) {
        Log.d("VacanciesID", id)
        val action = VacancySearchFragmentDirections.actionVacancySearchFragmentToVacancyDetailsFragment(vacancyId = id)
        findNavController().navigate(action)
    }

    private fun initUI() {
        binding.header.toolbarTitle.text = getString(R.string.search_vacancy)
        binding.header.iconFilter.isVisible = true
        binding.recyclerViewSearch.addItemDecoration(
            TopSpacingItemDecoration(
                dpToPx(RECYCLER_MARGIN_TOP, requireContext())
            )
        )

        binding.header.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }
    }

    private fun observeViewModel() {
        searchViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacanciesState.Initial -> showInitialState()
                is VacanciesState.Loading -> showLoadingState()
                is VacanciesState.LoadingMore -> showLoadingMoreState()
                is VacanciesState.Success -> {
                    Log.d("Vacancies", state.vacancies.toString())
                    showSuccessState()
                    binding.searchMessage.text =
                        getString(R.string.found) + " ${state.totalFound} " + getString(R.string.vacancies)

                    adapter.submitList(state.vacancies.map { it.toUiModel() })

                    hasMore = state.hasMore
                    isLoading = false
                }

                VacanciesState.Empty -> showEmptyState()
                VacanciesState.NoInternet -> showNoInternetState()
                VacanciesState.ServerError -> showServerErrorState()
            }
        }

        searchViewModel.showToast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSearchInput() {
        binding.inputEditText.doOnTextChanged { text, start, before, count ->
            val query = text?.toString()
            val currentQuery = searchViewModel.getCurrentQuery()

            if (query == currentQuery) {
                if (query.isNotEmpty()) {
                    showNonEmptyInput()
                } else {
                    showEmptyInput()
                    searchViewModel.resetState()
                }
            }

            if (!query.isNullOrEmpty()) {
                showNonEmptyInput()
                debouncer?.submit {
                    activity?.runOnUiThread {
                        binding.progressBar.isVisible = true
                        searchViewModel.searchVacancies(query)
                    }
                }
            } else {
                showEmptyInput()
                searchViewModel.resetState()
            }
        }

        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.inputEditText.text.toString().trim()
                debouncer?.cancel()
                binding.progressBar.isVisible = true
                searchViewModel.searchVacancies(query)
                true
            }
            false
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSearch.adapter = adapter
        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val isNotLoadingAndHasMore = !isLoading && hasMore
                val isScrolledToEnd = visibleItemCount + firstVisibleItemPosition >= totalItemCount - 1
                val isFirstItemVisibleAndIsTotalCountValid =
                    firstVisibleItemPosition >= 0 && totalItemCount >= TOTAL_COUNT

                if (isNotLoadingAndHasMore && isScrolledToEnd && isFirstItemVisibleAndIsTotalCountValid) {
                    searchViewModel.loadMore()
                }
            }
        })
    }

    private fun showLoadingMoreState() {
        isLoading = true
        adapter.addLoadingFooter()
    }

    private fun showInitialState() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = false
        binding.searchMessage.isVisible = false
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.isVisible = true
        binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
    }

    private fun showLoadingState() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = true
        binding.searchMessage.isVisible = false
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.isVisible = false
    }

    private fun showSuccessState() {
        binding.errorText.isVisible = false
        binding.progressBar.isVisible = false
        binding.searchMessage.isVisible = true
        binding.recyclerViewSearch.isVisible = true
        binding.searchMainPlaceholder.visibility = View.GONE
        isLoading = false
        adapter.removeLoadingFooter()
    }

    private fun showEmptyState() {
        binding.progressBar.isVisible = false
        binding.searchMessage.isVisible = true
        binding.searchMessage.text = getString(R.string.no_vacancies)
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.isVisible = true
        binding.errorText.isVisible = true
        binding.errorText.text = getString(R.string.nothing_found)
        binding.searchMainPlaceholder.setImageResource(R.drawable.nothing_found_placeholder)
    }

    private fun showNoInternetState() {
        binding.searchMessage.isVisible = false
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.setImageResource(R.drawable.no_internet_placeholder)
        adapter.removeLoadingFooter()
        debounceForPlaceholder?.submit {
            activity?.runOnUiThread {
                binding.progressBar.isVisible = false
                binding.searchMainPlaceholder.isVisible = true
                binding.errorText.isVisible = true
                binding.errorText.text = getString(R.string.no_connection)
            }
        }
    }

    private fun showServerErrorState() {
        binding.searchMessage.isVisible = false
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.setImageResource(R.drawable.server_error_placeholder)
        debounceForPlaceholder?.submit {
            activity?.runOnUiThread {
                binding.progressBar.isVisible = false
                binding.searchMainPlaceholder.isVisible = true
                binding.errorText.isVisible = true
                binding.errorText.text = getString(R.string.server_error)
            }
        }
    }

    private fun showNonEmptyInput() {
        binding.icon.setImageResource(R.drawable.close_24px)
        binding.searchMainPlaceholder.isVisible = false
        binding.errorText.isVisible = false
        binding.icon.isClickable = true
        binding.icon.setOnClickListener {
            debouncer?.cancel()
            searchViewModel.resetState()
            adapter.submitList(emptyList())
            binding.errorText.isVisible = false
            binding.searchMessage.isVisible = false
            binding.progressBar.isVisible = false
            binding.recyclerViewSearch.isVisible = false
            binding.inputEditText.clearFocus()
            binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
            binding.inputEditText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    private fun showEmptyInput() {
        debouncer?.cancel()
        vacanciesList.clear()
        binding.searchMainPlaceholder.isVisible = true
        binding.icon.setImageResource(R.drawable.search_24px)
        binding.icon.isClickable = false
        binding.progressBar.isVisible = false
        binding.errorText.isVisible = false
        binding.searchMessage.isVisible = false
        binding.recyclerViewSearch.isVisible = false
        binding.searchMainPlaceholder.setImageResource(R.drawable.search_main_placeholder)
    }
}
