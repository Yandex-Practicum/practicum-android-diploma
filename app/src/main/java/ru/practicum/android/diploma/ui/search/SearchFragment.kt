package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage
import ru.practicum.android.diploma.ui.root.RootActivity

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }
    private var _adapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        toolbarSetup()
        viewModel.stateSearch.observe(viewLifecycleOwner) {
            Log.v("SEARCH", "state change curr state $it")
            render(it)
        }
        val currentState = viewModel.stateSearch.value
        val vacanciesList =
            if (currentState is SearchState.Content) currentState.vacancyPage.vacancyList else listOf()
        val vacanciesArrayList = arrayListOf<Vacancy>()
        vacanciesArrayList.addAll(vacanciesList)
        _adapter = VacancyAdapter(vacanciesArrayList, object : VacancyAdapter.OnClickListener {
            override fun onClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyId = vacancy.id)
            }
        })
        binding.rvSearch.adapter = _adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos =
                        (binding.rvSearch.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = _adapter?.itemCount ?: 0
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
        binding.etButtonSearch.doOnTextChanged { text, _, _, _ ->
            hideIconEditText(text)
            if (binding.etButtonSearch.hasFocus()) {
                _adapter!!.vacancyList.clear()
                viewModel.searchDebounce(text.toString())
            }
        }

        toolbar.menu.findItem(R.id.filters).setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtrationFragment)
            true
        }

        viewModel.newPageLoading.observe(viewLifecycleOwner) {
            renderNewPageLoading(it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        toolbarSetup()
    }

    private fun toolbarSetup() {
        toolbar.title = getString(R.string.title_home)
        toolbar.menu.findItem(R.id.filters)?.isVisible = true
        toolbar.menu.findItem(R.id.filters)?.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtrationFragment)
            true
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> renderSearchLoading()
            is SearchState.Default -> renderSearchDefault()
            is SearchState.Empty -> renderSearchEmpty()
            is SearchState.NoConnection -> renderSearchNoConnection()
            is SearchState.ServerError -> renderSearchError()
            is SearchState.Content -> renderSearchContent(
                state.vacancyPage,
                state.currencyDictionary
            )

            is SearchState.LastPage -> renderLastPage()
            is SearchState.NextPageError -> renderNewPageError()
        }
    }

    private fun renderNewPageError() {
        binding.progressBarBottom.isVisible = false
        Toast.makeText(context, R.string.search_server_error, Toast.LENGTH_LONG).show()
    }

    private fun renderLastPage() {
        binding.progressBarBottom.isVisible = false
    }

    private fun renderNewPageLoading(showBottomProgressBar: Boolean) {
        binding.progressBarBottom.isVisible = showBottomProgressBar
    }

    private fun renderSearchLoading() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = true
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun renderSearchDefault() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.search_man)
            ivPlaceholder.isVisible = true
            tvPlaceholder.isVisible = false
            etButtonSearch.setText("")
            hideKeyboard()
        }
    }

    private fun renderSearchEmpty() {
        with(binding) {
            tvButtonSearchResult.isVisible = true
            tvButtonSearchResult.setText(R.string.search_vacancy_not_found)
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.nothing_found_cat)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_vacancy_list_not_found)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderSearchError() {
        with(binding) {
            tvButtonSearchResult.isVisible = false
            rvSearch.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.server_error_towel)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_server_error)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderSearchNoConnection() {
        if (_adapter?.vacancyList?.size!! > 0) {
            Snackbar.make(
                binding.rvSearch,
                getString(R.string.search_no_connection),
                Snackbar.LENGTH_LONG
            ).show()
            with(binding) {
                tvButtonSearchResult.isVisible = true
                rvSearch.isVisible = true
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                ivPlaceholder.isVisible = false
                tvPlaceholder.isVisible = false
            }
        } else {
            with(binding) {
                tvButtonSearchResult.isVisible = false
                rvSearch.isVisible = false
                progressBar.isVisible = false
                progressBarBottom.isVisible = false
                ivPlaceholder.setImageResource(R.drawable.no_internet_scull)
                ivPlaceholder.isVisible = true
                tvPlaceholder.setText(R.string.search_no_connection)
                tvPlaceholder.isVisible = true
            }
        }
    }

    private fun renderSearchContent(
        vacancyPage: VacancyPage,
        currencyDictionary: Map<String, Currency>
    ) {
        _adapter?.vacancyList?.clear()
        _adapter?.vacancyList?.addAll(vacancyPage.vacancyList)
        _adapter?.currencyDictionary?.clear()
        _adapter?.currencyDictionary?.putAll(currencyDictionary)
        _adapter?.notifyDataSetChanged()
        with(binding) {
            tvButtonSearchResult.text =
                requireContext().getString(R.string.found_vacancies_count, vacancyPage.found)
            tvButtonSearchResult.isVisible = true
            rvSearch.isVisible = true
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun openFragmentVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacanciesFragment,
            Bundle().apply { putString("vacancy_model", vacancyId) }
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.etButtonSearch.windowToken, 0)
        binding.etButtonSearch.isEnabled = true
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    private fun hideIconEditText(text: CharSequence?) {
        val editText = binding.etButtonSearch
        if (text.isNullOrEmpty()) {
            binding.etButtonSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.search_icon,
                0
            )
            editText.setOnTouchListener { _, motionEvent ->
                false
            }
        } else {
            binding.etButtonSearch.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.clean_icon_black,
                0
            )
            val iconClear = editText.compoundDrawables[2]
            editText.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_UP &&
                    motionEvent.rawX >= editText.right - iconClear.bounds.width() * 2
                ) {
                    editText.isEnabled = false
                    viewModel.setStateDefault()
                }
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar.setNavigationIcon(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
