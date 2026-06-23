package ru.practicum.android.diploma.ui.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancySearchBinding
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.ui.adapter.VacancyAdapter
import ru.practicum.android.diploma.ui.viewmodels.SearchState
import ru.practicum.android.diploma.ui.viewmodels.SearchViewModel
import ru.practicum.android.diploma.util.ViewStateHelper
import ru.practicum.android.diploma.util.debounce

class VacancySearchFragment : Fragment() {
    private var _binding: FragmentVacancySearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var viewStateHelper: ViewStateHelper
    private var adapter: VacancyAdapter? = null
    private val onVacancySearchDebounce: (VacancyCard) -> Unit by lazy {
        debounce<VacancyCard>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            findNavController().navigate(
                R.id.action_vacancySearchFragment_to_vacancyDetailsFragment,
                // добавить вызов и передачу vacancyId или vacancy в VacancyDetailsFragment.createArgs(?)
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacancySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewStateHelper = ViewStateHelper(
            listOf(
                binding.layoutInitial.root,
                binding.layoutNoInternet.root,
                binding.layoutNoFound.root,
                binding.layoutServerError.root,
                binding.layoutLoading.root
            )
        )

        setupInitialState()
        setupNoInternetState()
        setupNoFoundState()
        setupServerErrorState()

        adapter = VacancyAdapter { clickOnVacancy(it) }
        binding.vacancyList.adapter = adapter
        binding.vacancyList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchState.IsLoading -> showLoadingState()
                SearchState.IsLoadingNextPage -> {
                    showLoadingState()
                }
                is SearchState.Content -> showContent(state.pageData, state.listNeedsScrollTop)
                is SearchState.ConnectionError -> showNoInternetState()
                is SearchState.NotFoundError -> showEmptyResultState()
                is SearchState.VacanciesCount -> {
                    if (state.vacanciesCount == 0) {
                        showEmptyResultState()
                    } else {
                        binding.tvResultInfo.isVisible = true
                        binding.tvResultInfo.text = getString(
                            R.string.founded_vacancies_count,
                            state.vacanciesCount.toString()
                        )
                    }
                }
                is SearchState.ServerError500 -> showServerErrorState()
                is SearchState.QueryIsEmpty -> {
                    if (state.isEmpty) showInitialState()
                }
                is SearchState.SearchText -> {

                }
            }
        }

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_filtersFragment)
        }

        binding.searchIcon.setOnClickListener {
            binding.searchEditText.setText("")
            showInitialState()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.searchIcon.setImageResource(R.drawable.ic_search_24)
                    showInitialState()
                } else {
                    binding.searchIcon.setImageResource(R.drawable.ic_close_24)
                    viewModel.searchDebounce(searchQuery = s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }

        binding.vacancyList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos =
                        (binding.vacancyList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (pos >= adapter!!.itemCount - 1) {
                        viewModel.onLastItemReached()
                    }
                    viewModel.setNoScrollOnViewCreated()
                }
            }
        })

        binding.searchEditText.addTextChangedListener(textWatcher)
    }

    private fun setupInitialState() {
        binding.layoutInitial.ivPlaceholderPicture.setImageResource(R.drawable.main_screen)
        binding.layoutInitial.tvPlaceholderText.isVisible = false
    }

    private fun setupNoInternetState() {
        binding.layoutNoInternet.ivPlaceholderPicture.setImageResource(R.drawable.placeholder_no_internet)
        binding.layoutNoInternet.tvPlaceholderText.text = getString(R.string.no_internet)
        binding.layoutNoInternet.tvPlaceholderText.isVisible = true
    }

    private fun setupNoFoundState() {
        binding.layoutNoFound.ivPlaceholderPicture.setImageResource(R.drawable.placeholder_no_found)
        binding.layoutNoFound.tvPlaceholderText.text = getString(R.string.error_fetching_vacancies)
        binding.layoutNoFound.tvPlaceholderText.isVisible = true
    }

    private fun setupServerErrorState() {
        binding.layoutServerError.ivPlaceholderPicture.setImageResource(R.drawable.placeholder_error_server)
        binding.layoutServerError.tvPlaceholderText.text = getString(R.string.server_error)
        binding.layoutServerError.tvPlaceholderText.isVisible = true
    }

    private fun showInitialState() {
        viewStateHelper.showOnly(binding.layoutInitial.root)
        binding.tvResultInfo.isVisible = false
        binding.vacancyList.isVisible = false
    }

    private fun showNoInternetState() {
        viewStateHelper.showOnly(binding.layoutNoInternet.root)
        binding.tvResultInfo.isVisible = false
    }

    private fun showEmptyResultState() {
        viewStateHelper.showOnly(binding.layoutNoFound.root)
        binding.tvResultInfo.isVisible = true
        binding.tvResultInfo.text = getString(R.string.no_vacancies)
    }

    private fun showServerErrorState() {
        viewStateHelper.showOnly(binding.layoutServerError.root)
        binding.tvResultInfo.isVisible = false
    }

    private fun showLoadingState() {
        closeKeyboard()
        viewStateHelper.showOnly(binding.layoutLoading.root)
        binding.tvResultInfo.isVisible = false
    }

    private fun showContent(searchData: List<VacancyCard>, listNeedsScrollTop: Boolean) {
        viewStateHelper.showOnly(binding.vacancyList)
        if (!binding.vacancyList.isVisible) {
            binding.vacancyList.isVisible = true
        }
        showFoundVacancies(vacancies = searchData, scrollToTop = listNeedsScrollTop)
    }

    private fun showFoundVacancies(vacancies: List<VacancyCard>? = null, scrollToTop: Boolean = false) {
        if (adapter == null)
            return

        adapter?.submitList(vacancies)
        adapter?.notifyDataSetChanged()
        if (scrollToTop) {
            binding.vacancyList.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clickOnVacancy(vacancy: VacancyCard) {
        findNavController().navigate(
            R.id.action_vacancySearchFragment_to_vacancyDetailsFragment,
            VacancyDetailsFragment.createArgs(vacancy.vacancyId)
        )
    }

    private fun closeKeyboard() {
        requireActivity().currentFocus?.let { view ->
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
