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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.model.AdapterState
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.SearchViewState
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.presentation.list_items.ListItem
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.search.ui.decorations.LayoutItemDecoration

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private var adapter: SearchAdapter? = null
    private val handler: Handler = Handler(Looper.getMainLooper())
    private var textInput = ""
    private var isNewQueryLoading = false

    val vacancyItem = VacancyItems(
        "1",
        "Программист",
        "Москва",
        "Sber",
        null,
        Salary(
            40000,
            100000,
            "RUR"
        )
    )

    private var vacancyList = arrayListOf(
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem,
        vacancyItem
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterSettingsFragment)
        }

        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

        binding.initScreenPH.isVisible = true

        adapter = SearchAdapter(viewModel::showVacancyDetails)
        binding.searchVacanciesRV.adapter = adapter

        binding.searchVacanciesRV.addItemDecoration(LayoutItemDecoration(86))

        binding.clearIcon.setOnClickListener {
            onClearIconPressed()
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)

        }

        binding.textInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchVacancy(textInput)
                true
            }
            false
        }

        val editTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, ncout: Int) {

                with(binding) {
                    if (textInput.hasFocus() && s?.isEmpty() == true) {
                        initScreenPH.isVisible = false
                        textHint.isVisible = false
                        searchVacanciesRV.isVisible = false
                        noVacanciesFoundPH.isVisible = false
                        noConnectionPH.isVisible = false
                    } else {
                        searchVacanciesRV.isVisible = false
                    }
                }
                textInput = s.toString()
                processNewQuery(textInput)
                binding.clearIcon.visibility = clearButtonVisibility(s)
                binding.searchIcon.visibility = searchIconVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        binding.textInput.addTextChangedListener(editTextWatcher)

        binding.searchVacanciesRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("OnScroll", "scrollEvent")
                if (dy > 0) {
                    val pos =
                        (binding.searchVacanciesRV.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter!!.itemCount
                    if (pos >= itemsCount - 1) {
                        loadNextPage()
                        Log.d("VacancyItems", "${vacancyList.size}")
                    }
                }
            }
        })

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            render(state)

//            if (state is SearchViewState.Content) {
//                Log.d("DetailsState", "${state}")
//
//                handler.postDelayed(
//                    {
//                        adapter?.hideLoading()
//                    },
//                    2000
//                )
//            }
        }

        viewModel.getAdapterStateLiveData().observe(viewLifecycleOwner){ adapterState ->
            Log.d("adapterState", "$adapterState")
            renderAdapterState(adapterState)
        }

        viewModel.getShowVacancyDetails().observe(viewLifecycleOwner) { vacancyId ->
            showVacancyDetails(vacancyId)
        }
    }

    private fun processNewQuery(query: String) {
        viewModel.searchDebounce(query)
//        isNewQueryLoading = true
    }

    private fun renderAdapterState(state: AdapterState){
        Log.d("AdapterState", "$state")
        when(state) {
            is AdapterState.IsLoading -> {
                adapter?.showLoading()
//                handler.postDelayed(
//                    {adapter?.hideLoading()},
//                    2000
//                )
            }
            is AdapterState.Idle -> {
                handler.postDelayed(
                    {adapter?.hideLoading()},
                    1000
                )
            }
//                adapter?.hideLoading()


        }
    }

    private fun render(state: SearchViewState) {
        when (state) {
            is SearchViewState.Content -> showContent(state)
            is SearchViewState.Loading -> showMainProgressBar()
            is SearchViewState.ConnectionError -> showNoConnectionPH()
            is SearchViewState.NotFoundError -> showNoVacanciesFoundPH()
            is SearchViewState.ServerError -> showServerErrorPH()
            else -> {}
        }
    }

    private fun showNoVacanciesFoundPH() {
        with(binding) {
            noVacanciesFoundPH.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            serverErrorPH.isVisible = false
            noConnectionPH.isVisible = false
            mainProgressBar.isVisible = false
            searchVacanciesRV.isVisible = false
        }
    }

    private fun showServerErrorPH() {
        with(binding) {
            serverErrorPH.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            noConnectionPH.isVisible = false
            mainProgressBar.isVisible = false
            searchVacanciesRV.isVisible = false
            noVacanciesFoundPH.isVisible = false
        }
    }

    private fun showNoConnectionPH() {
        with(binding) {
            noConnectionPH.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            mainProgressBar.isVisible = false
            searchVacanciesRV.isVisible = false
            noVacanciesFoundPH.isVisible = false
            serverErrorPH.isVisible = false
        }
    }

    private fun showMainProgressBar() {
        with(binding) {
            mainProgressBar.isVisible = true
            initScreenPH.isVisible = false
            textHint.isVisible = false
            searchVacanciesRV.isVisible = false
            noVacanciesFoundPH.isVisible = false
            noConnectionPH.isVisible = false
            serverErrorPH.isVisible = false
        }
    }

    private fun showAdapterLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> adapter?.showLoading()
            false -> adapter?.hideLoading()
        }
    }

    private fun loadNextPage() {
        viewModel.onLastItemReached(textInput)
    }

    private fun onClearIconPressed() {
        with(binding) {
            textInput.setText("")
            adapter?.submitData(emptyList())
            initScreenPH.isVisible = true
            searchVacanciesRV.isVisible = false
            textHint.isVisible = false
            noConnectionPH.isVisible = false
            serverErrorPH.isVisible = false

        }
    }

    private fun showContent(state: SearchViewState.Content) {
        with(binding) {
//            adapter?.hideLoading()
            val position = state.listItem.lastIndex
//            if(!(state.listItem.get(position) is ListItem.LoadingItem)){
//                val mutableList = state.listItem.toMutableList()
//                mutableList.add(ListItem.LoadingItem)
//            }

//            val updatedList = state.listItem.toMutableList().apply {
//                removeAll { it is ListItem.LoadingItem }
//            }
//
            adapter?.submitData(state.listItem)
            textHint.text = state.vacanciesFoundHint
            textHint.isVisible = true
            searchVacanciesRV.isVisible = true
            mainProgressBar.isVisible = false
            noVacanciesFoundPH.isVisible = false
            noConnectionPH.isVisible = false
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun searchIconVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showVacancyDetails(vacancyId: String) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment,
        )
    }

    companion object {
        private const val VACANCY_ID_KEY = "vacancy_id_key"
    }
}



