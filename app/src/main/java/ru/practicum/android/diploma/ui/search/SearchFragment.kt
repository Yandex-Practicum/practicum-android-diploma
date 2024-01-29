package ru.practicum.android.diploma.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchFragment : Fragment() {
    private var countValue = ""
    private val vacancyList = ArrayList<Vacancy>()
    private var adapter = VacancyAdapter()
    private var isClickAllowed = true
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }





        binding.inputSearchForm.addTextChangedListener(simpleTextWatcher)

        viewModel.observeClearTextState().observe(viewLifecycleOwner) { clearTextState ->
            if (clearTextState is ClearTextState.ClearText) {
                clearSearchText()

                viewModel.textCleared()
            }
        }

    }

    private fun initViews() {

        adapter.vacancyList = vacancyList
        binding.rvSearch.adapter = adapter


    }

    private val simpleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            viewModel.onTextChanged(s.toString())
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
    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.SearchContent -> showSearchResult(state.vacansys)
            is SearchState.EmptySearch -> showNotFound()
            is SearchState.Error -> showNotConnected()
            is SearchState.EmptyScreen -> showEmptyScreen()
        }
    }

    private fun showLoading() {
        showEmptyScreen()
        binding.progressBar.visibility = VISIBLE
    }



    private fun showEmptyScreen() {
        binding.rvSearch.visibility = GONE
        binding.progressBar.visibility = GONE
        binding.errorVacancyImage.visibility = GONE
        binding.tvError.visibility = GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showSearchResult(vacancys: List<Vacancy>) {
        showEmptyScreen()
        binding.rvSearch.visibility = VISIBLE
        vacancyList.clear()
        vacancyList.addAll(vacancys)
        adapter.vacancyList = vacancyList
        adapter.notifyDataSetChanged()
    }



    private fun showNotFound() {
        showEmptyScreen()
        binding.tvError.visibility = VISIBLE
        binding.errorVacancyImage.visibility = VISIBLE
        binding.progressBar.visibility = GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showNotConnected() {
        showEmptyScreen()
        binding.notInternetImage.visibility = VISIBLE
        binding.tvNotInternet.visibility = VISIBLE
        adapter.vacancyList.clear()
        adapter.notifyDataSetChanged()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_VALUE, countValue)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        simpleTextWatcher.let { binding.inputSearchForm.removeTextChangedListener(it) }
        viewModel.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        isClickAllowed = true
    }

    private fun clearSearchText() {
        binding.inputSearchForm.text.clear()
        binding.closeImage.visibility = GONE
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        const val SEARCH_VALUE = "SEARCH_VALUE"
        const val CLICK_DEBOUNCE_DELAY = 1000L


    }
}
