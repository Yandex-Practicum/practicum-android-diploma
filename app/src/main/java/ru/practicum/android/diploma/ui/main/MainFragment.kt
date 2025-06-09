package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.main.models.SearchContentStateVO

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<MainViewModel>()

    private var vacanciesAdapter: SearchResultsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacanciesAdapter = SearchResultsAdapter(
            clickListener = { viewModel.onVacancyClick(it) },
            requireContext(),
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { return }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChange(s.toString())
            }

            override fun afterTextChanged(s: Editable?) { return }
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
            is SearchContentStateVO.Loading -> showLoadingState()
            is SearchContentStateVO.Error -> showErrorState(state.noInternet)
            is SearchContentStateVO.Success -> showSearchResults(state.tracks)
        }
    }

    private fun showBaseView() {
        binding.searchBaseState.visibility = VISIBLE
        binding.progress.visibility = INVISIBLE
        binding.noInternetError.visibility = INVISIBLE
        binding.unknownError.visibility = INVISIBLE
        binding.searchResults.visibility = INVISIBLE
    }

    private fun showLoadingState() {
        binding.searchBaseState.visibility = INVISIBLE
        binding.progress.visibility = VISIBLE
        binding.noInternetError.visibility = INVISIBLE
        binding.unknownError.visibility = INVISIBLE
        binding.searchResults.visibility = INVISIBLE
    }

    private fun showSearchResults(newVacancies: List<Vacancy>) {
        binding.searchBaseState.visibility = INVISIBLE
        binding.progress.visibility = INVISIBLE
        binding.noInternetError.visibility = INVISIBLE
        binding.unknownError.visibility = INVISIBLE
        binding.searchResults.visibility = VISIBLE

        vacanciesAdapter?.vacancies?.clear()
        vacanciesAdapter?.vacancies?.addAll(newVacancies)
        vacanciesAdapter?.notifyDataSetChanged()
    }

    private fun showErrorState(isNoInternetError: Boolean) {
        binding.searchBaseState.visibility = INVISIBLE
        binding.progress.visibility = INVISIBLE
        binding.noInternetError.visibility = if (isNoInternetError) VISIBLE else INVISIBLE
        binding.unknownError.visibility = if (isNoInternetError) INVISIBLE else VISIBLE
        binding.searchResults.visibility = INVISIBLE
    }
}
