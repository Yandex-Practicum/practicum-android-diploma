package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModel<SearchViewModel>()
    private var vacancyClickDebounce: ((Vacancy) -> Unit)? = null

    // также надо переделать на searchDebouns
    private var vacancyAdapter = VacancyAdapter {
        vacancyClickDebounce?.let { vacancyClickDebounce -> vacancyClickDebounce(it) }
    }
    private var recyclerView: RecyclerView? = null
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        vacancyAdapter = VacancyAdapter {
            vacancyClickDebounce?.let { vacancyClickDebounce -> vacancyClickDebounce(it) }
        }

        clickAdapting()
        binding.inputSearchForm.addTextChangedListener(simpleTextWatcher)
        onClearIconClick()
        startSearchByEnterPress()

        recyclerView = binding.rvSearch
        recyclerView!!.adapter = vacancyAdapter

    }

    // метод восстанавливает поисковой запрос после пересоздания
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getString(SEARCH_USER_INPUT, "")
    }

    private fun render(stateLiveData: SearchState) {
        when (stateLiveData) {
            is SearchState.Loading -> loading()
            is SearchState.SearchContent -> searchIsOk(stateLiveData.vacancys)
            is SearchState.EmptySearch -> nothingFound()
            is SearchState.Error -> connectionError()
            is SearchState.EmptyScreen -> defaultSearch()
        }
    }

    private fun clickAdapting() {
        vacancyClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) {
            val bundle = bundleOf("vacancy" to it)
            findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
        }
    }

    private fun search() {
        searchViewModel.searchRequest(binding.inputSearchForm.text.toString())
    }

    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            binding.searchImage.visibility = VISIBLE
            binding.closeImage.visibility = GONE
        }

        override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            startSearchByEnterPress()
        }

        override fun afterTextChanged(editable: Editable?) {
            if (binding.inputSearchForm.text.isEmpty()) {
                binding.searchImage.visibility = VISIBLE
                binding.closeImage.visibility = GONE
            } else {
                binding.searchImage.visibility = GONE
                binding.closeImage.visibility = VISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun startSearchByEnterPress() {
        binding.inputSearchForm.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.inputSearchForm.text.isNotEmpty()) {
                binding.inputSearchForm.text.toString()
                search()
                vacancyAdapter.notifyDataSetChanged()
            }
            false
        }
    }

    private fun onClearIconClick() {
        binding.closeImage.setOnClickListener {
            binding.inputSearchForm.setText("")
            val keyboard =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(
                binding.inputSearchForm.windowToken,
                0
            )
            binding.inputSearchForm.clearFocus()
        }
    }

    private fun defaultSearch() {
        recyclerView?.visibility = View.VISIBLE
        binding.notInternetImage.visibility = View.GONE
        binding.errorVacancyImage.visibility = View.GONE
        Log.d("DefaultSearch", "DefaultSearch was started")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loading() {
        binding.progressBar.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        binding.notInternetImage.visibility = View.GONE
        binding.errorVacancyImage.visibility = View.GONE
        vacancyAdapter.notifyDataSetChanged()
        Log.d("Loading", "Loading was started")
    }

    private fun searchIsOk(data: List<Vacancy>) {
        binding.progressBar.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
        binding.notInternetImage.visibility = View.GONE
        binding.errorVacancyImage.visibility = View.GONE
        binding.closeImage.visibility - View.GONE
        vacancyAdapter.vacancyList.addAll(data)
        Log.d("SearchIsOk", "Loading has been end")
    }

    private fun nothingFound() {
        binding.progressBar.visibility = View.GONE
        binding.closeImage.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        binding.errorVacancyImage.visibility = View.VISIBLE
        binding.notInternetImage.visibility = View.GONE
        Log.d("NothingFound", "NothingFound")
    }

    private fun connectionError() {
        binding.progressBar.visibility = View.GONE
        binding.notInternetImage.visibility = View.VISIBLE
        binding.errorVacancyImage.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        Log.d("ConnectionError", "Connection Error")
    }

    companion object {
        private const val SEARCH_USER_INPUT = "SEARCH_USER_INPUT"
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
