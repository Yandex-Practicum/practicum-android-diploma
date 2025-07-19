package ru.practicum.android.diploma.ui.vacancysearch.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.Callbacks
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.StateHandlers
import ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils.UiComponents
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.TopSpacingItemDecoration
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Debouncer
import ru.practicum.android.diploma.util.dpToPx

class VacancySearchFragment : Fragment(), VacancyItemAdapter.Listener {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModel<VacanciesSearchViewModel>()
    private var ui: VacancySearchUi? = null

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

        val uiComponents = UiComponents(
            binding = binding,
            adapter = adapter,
            clearFocusView = view,
            context = requireContext(),
            activity = requireActivity()
        )

        val stateHandlers = StateHandlers(
            debouncer = debouncer,
            debounceForPlaceholder = debounceForPlaceholder,
            viewModel = searchViewModel,
            vacancyList = vacanciesList
        )

        val callbacks = Callbacks(
            onClear = { vacanciesList.clear() }
        )

        ui = VacancySearchUi(
            ui = uiComponents,
            state = stateHandlers,
            callbacks = callbacks
        )

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
                is VacanciesState.Initial -> ui?.showInitialState()
                is VacanciesState.Loading -> ui?.showLoadingState()
                is VacanciesState.LoadingMore -> ui?.showLoadingMoreState()
                is VacanciesState.Success -> {
                    Log.d("Vacancies", state.vacancies.toString())
                    ui?.showSuccessState()
                    binding.searchMessage.text =
                        getString(R.string.found) + " ${state.totalFound} " + getString(R.string.vacancies)

                    adapter.submitList(state.vacancies.map { it.toUiModel() })

                    hasMore = state.hasMore
                    isLoading = false
                }

                VacanciesState.Empty -> ui?.showEmptyState()
                VacanciesState.NoInternet -> ui?.showNoInternetState()
                VacanciesState.ServerError -> ui?.showServerErrorState()
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
                    ui?.showNonEmptyInput()
                } else {
                    ui?.showEmptyInput()
                    searchViewModel.resetState()
                }
            }

            if (!query.isNullOrEmpty()) {
                ui?.showNonEmptyInput()
                debouncer?.submit {
                    activity?.runOnUiThread {
                        binding.progressBar.isVisible = true
                        searchViewModel.searchVacancies(query)
                    }
                }
            } else {
                ui?.showEmptyInput()
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
}
