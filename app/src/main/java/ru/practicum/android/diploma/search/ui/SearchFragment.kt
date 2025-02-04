package ru.practicum.android.diploma.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.presentation.SearchAdapter
import ru.practicum.android.diploma.common.presentation.decorations.LayoutItemDecoration
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyFragment

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var adapter: SearchAdapter? = null
    private val handler = Handler(Looper.getMainLooper())
    private var job: Job? = null
    private var textInput: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        adapter = null
        handler.removeCallbacksAndMessages(null)
        _binding = null
        job = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupRecyclerView()
        setupTextInput()
        setupClearIcon()
        observeViewModel()
    }

    private fun setupNavigation() {
        binding.filterButton.setOnClickListener { findNavController().navigate(
            R.id.action_searchFragment_to_filterSettingsFragment
        ) }
    }

    private fun setupRecyclerView() {
        adapter = SearchAdapter(viewModel::showVacancyDetails)
        binding.searchVacanciesRV.adapter = adapter
        binding.searchVacanciesRV.addItemDecoration(
            LayoutItemDecoration(FIRST_ITEM_MARGIN_TOP).apply { init(requireContext()) }
        )
        binding.searchVacanciesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("OnScroll", "scrollEvent")
                if (dy > 0) {
                    val pos =
                        (binding.searchVacanciesRV.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter!!.itemCount - 1
                    Log.d("OnScroll", "pos: $pos, itemsCount: $itemsCount")
                    if (pos >= itemsCount) {
                        handler.postDelayed(
                            { loadNextPage() },
                            DELAY_500
                        )
                    }
                }
            }
        })
    }

    private fun setupTextInput() {
        viewModel.renderFilterState()
        binding.textInput.requestFocus()
        binding.textInput.setOnEditorActionListener { _, actionId, _ -> if (actionId == EditorInfo.IME_ACTION_DONE) {
            viewModel.searchVacancy(
                binding.textInput.text.toString()
            ).let { true }
        } else {
            false
        } }
        binding.textInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) = updateVisibilityBasedOnInput(s).also {
                textInput = s.toString()
                if (s.isNullOrEmpty()) {
                    binding.clearIcon.visibility = View.GONE
                    viewModel.clearSearchList()
                    adapter?.submitList(emptyList())
                    binding.searchVacanciesRV.adapter = adapter
                } else { binding.clearIcon.visibility = View.VISIBLE }
                binding.searchIcon.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s: Editable?) { searchOnTextChanged(s.toString()) }
        })
    }

    private fun setupClearIcon() {
        binding.clearIcon.setOnClickListener { onClearIconPressed(); (requireContext().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as? InputMethodManager)?.hideSoftInputFromWindow(view?.windowToken, 0) }
    }

    private fun updateVisibilityBasedOnInput(s: CharSequence?) = with(binding) {
        initScreenPH.isVisible = textInput.hasFocus() && s.isNullOrEmpty()
        textHint.isVisible = !initScreenPH.isVisible
        searchVacanciesRV.isVisible = !initScreenPH.isVisible
        noVacanciesFoundPH.isVisible = false
        noConnectionPH.isVisible = false
    }

    private fun observeViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { render(it) }
        viewModel.getAdapterStateLiveData().observe(viewLifecycleOwner) { renderAdapterState(it) }
        viewModel.showVacancyDetails.observe(
            viewLifecycleOwner
        ) { it?.let { showVacancyDetails(it); viewModel.resetVacancyDetails() } }
        viewModel.observeFilterStateLiveData().observe(viewLifecycleOwner) { isChecked ->
            Log.d("FilterFragmentState", "$isChecked")
            renderFilterState(isChecked)
        }
    }

    private fun renderFilterState(isChecked: Boolean) {
        when (isChecked) {
            true -> binding.filterButton.setImageResource(R.drawable.ic_filter_applied)
            false -> binding.filterButton.setImageResource(R.drawable.ic_button_filter)
        }
    }

    private fun renderAdapterState(state: AdapterState) = when (state) {
        is AdapterState.IsLoading -> adapter?.showLoading()
        is AdapterState.Idle -> hideAdapterLoading()
    }

    private fun render(state: SearchViewState) = when (state) {
        is SearchViewState.Content -> showContent(state)
        is SearchViewState.Loading -> showMainProgressBar()
        is SearchViewState.ConnectionError -> showNoConnectionPH()
        is SearchViewState.NotFoundError -> showNoVacanciesFoundPH()
        is SearchViewState.ServerError -> showServerErrorPH()
        is SearchViewState.Base -> showBaseView()
        else -> {}
    }

    private fun showNoConnectionPH() {
        when {
            adapter?.currentList.isNullOrEmpty() -> showMainNoConnectionPH()
            job?.isActive != true -> showPaginationNoConnectionPH()
        }
    }

    private fun showNoVacanciesFoundPH() {
        when {
            adapter?.currentList.isNullOrEmpty() -> showMainNoVacanciesFoundPH()
            job?.isActive != true -> showPaginationNoVacanciesPH()
        }
    }

    private fun showServerErrorPH() {
        when {
            adapter?.currentList.isNullOrEmpty() -> showMainServerErrorPH()
            job?.isActive != true -> showPaginationServerErrorPH()
        }
    }

    private fun showBaseView() = with(binding) {
        noConnectionPH.isVisible = false
        initScreenPH.isVisible = true
        textHint.isVisible = false
        mainProgressBar.isVisible = false
        searchVacanciesRV.isVisible = false
        noVacanciesFoundPH.isVisible = false
        serverErrorPH.isVisible = false
    }

    private fun showMainNoConnectionPH() = with(binding) {
        noConnectionPH.isVisible = true
        noConnectionTextHint.isVisible = true
        initScreenPH.isVisible = false
        textHint.isVisible = false
        mainProgressBar.isVisible = false
        searchVacanciesRV.isVisible = false
        noVacanciesFoundPH.isVisible = false
        serverErrorPH.isVisible = false
    }

    private fun showMainNoVacanciesFoundPH() = with(binding) {
        textHint.text = NO_VACANCIES_FOUND
        textHint.isVisible = true
        noVacanciesFoundPH.isVisible = true
        initScreenPH.isVisible = false
        serverErrorPH.isVisible = false
        noConnectionPH.isVisible = false
        mainProgressBar.isVisible = false
        searchVacanciesRV.isVisible = false
    }

    private fun showMainServerErrorPH() = with(binding) {
        serverErrorPH.isVisible = true
        initScreenPH.isVisible = false
        textHint.isVisible = false
        noConnectionPH.isVisible = false
        mainProgressBar.isVisible = false
        searchVacanciesRV.isVisible = false
        noVacanciesFoundPH.isVisible = false
    }

    private fun showPaginationServerErrorPH() = launchJob(SERVER_ERROR)
    private fun showPaginationNoVacanciesPH() = launchJob(ERROR_NO_VACANCIES_FOUND)
    private fun showPaginationNoConnectionPH() = launchJob(CHECK_YOUR_CONNECTION)

    private fun showMainProgressBar() = with(binding) {
        mainProgressBar.isVisible = true
        initScreenPH.isVisible = false
        textHint.isVisible = false
        searchVacanciesRV.isVisible = false
        noVacanciesFoundPH.isVisible = false
        noConnectionPH.isVisible = false
        serverErrorPH.isVisible = false
    }

    private fun hideAdapterLoading() = handler.postDelayed({ adapter?.hideLoading() }, DELAY_1000)

    private fun loadNextPage() = viewModel.onLastItemReached(textInput)

    private fun searchOnTextChanged(query: String) = with(binding) {
        textHint.isVisible = false
        noConnectionTextHint.isVisible = false
        noVacanciesFoundPH.isVisible = false
        serverErrorPH.isVisible = false
        viewModel.searchDebounce(query)
        adapter?.submitList(emptyList())
    }

    private fun onClearIconPressed() = with(binding) {
        viewModel.declineLastSearch()
        textInput.setText("")
        adapter?.submitList(emptyList())
        initScreenPH.isVisible = true
        searchVacanciesRV.adapter = adapter
        searchVacanciesRV.isVisible = false
        textHint.isVisible = false
        noConnectionPH.isVisible = false
        serverErrorPH.isVisible = false
        mainProgressBar.isVisible = false
    }

    private fun showContent(state: SearchViewState.Content) = with(binding) {
        job?.cancel()
        adapter?.submitData(state.listItem)
        textHint.text = state.vacanciesFoundHint
        textHint.isVisible = true
        searchVacanciesRV.isVisible = true
        mainProgressBar.isVisible = false
        noVacanciesFoundPH.isVisible = false
        noConnectionPH.isVisible = false
        initScreenPH.isVisible = false
    }

    private fun showVacancyDetails(vacancyId: String) = findNavController()
        .navigate(R.id.action_searchFragment_to_vacancyFragment, VacancyFragment.createArgs(vacancyId))

    private fun launchJob(message: String) {
        job = CoroutineScope(Dispatchers.Main).launch {
            hideAdapterLoading()
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            delay(DELAY_2000)
        }
    }

    companion object {
        private const val FIRST_ITEM_MARGIN_TOP = 46
        private const val DELAY_2000 = 2_000L
        private const val DELAY_1000 = 1_000L
        private const val DELAY_500 = 500L
        private const val NO_VACANCIES_FOUND = "Таких вакансий нет"
        private const val ERROR_NO_VACANCIES_FOUND = "Ошибка. Вакансии не найдены"
        private const val CHECK_YOUR_CONNECTION = "Проверьте подключение"
        private const val SERVER_ERROR = "Ошибка сервера"
    }
}
