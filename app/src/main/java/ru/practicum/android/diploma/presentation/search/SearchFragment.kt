package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.SearchState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<SearchViewModel>()
    private val binding get() = _binding!!
    private var inputText: String = ""
    private var simpleTextWatcher: TextWatcher? = null
    lateinit var onItemClickDebounce: (Vacancy) -> Unit

    private val vacancies = mutableListOf<Vacancy>()
    private val adapter = SearchAdapter(vacancies) { vacanciy ->
        onItemClickDebounce(vacanciy)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearch.adapter = adapter
        onItemClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
//            DetailFragment.addArgs(vacancy)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }
        binding.clearButtonIcon.setOnClickListener {
            if (binding.searchEt.text.isNotEmpty()) {
                binding.searchEt.setText("")
                viewModel.clearInputEditText()
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.clearButtonIcon.windowToken, 0)
            }
        }
        simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputText = s?.toString() ?: ""
                viewModel.searchDebounce(inputText)
                if (binding.searchEt.text.isNotEmpty()) {
                    binding.clearButtonIcon.setImageResource(R.drawable.ic_clear_et)
                } else clearEditText()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        simpleTextWatcher?.let { binding.searchEt.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.searchEt.text.isNotEmpty()) {
                    viewModel.searchDebounce(inputText)
                    binding.clearButtonIcon.setImageResource(R.drawable.ic_clear_et)
                } else clearEditText()
            }
            false
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.Content -> showContent(state.vacancies,state.foundValue)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.startImage.isVisible = false
        binding.progressBar.isVisible = true
        binding.rvSearch.isVisible = false
        binding.searchCount.isVisible = false
        binding.placeholderMessage.isVisible = false
    }

    private fun showContent(searchVacancies: List<Vacancy>, foundValue: Int) {
        binding.startImage.isVisible = false
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = true
        binding.searchCount.isVisible = true
        binding.searchCount.text = foundValue.toString() + "шт"
        binding.placeholderMessage.isVisible = false
        vacancies.clear()
        vacancies.addAll(searchVacancies)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty(message: String) {
        binding.startImage.isVisible = false
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.searchCount.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageImage.setImageResource(R.drawable.search_placeholder_nothing_found)
        binding.placeholderMessageText.text = message
    }

    private fun showError(errorMessage: String) {
        binding.startImage.isVisible = false
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.searchCount.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageText.text = errorMessage
    }

    private fun clearEditText() {
        binding.clearButtonIcon.setImageResource(R.drawable.ic_clear_et)
        binding.startImage.isVisible = true
        binding.progressBar.isVisible = false
        binding.rvSearch.isVisible = false
        binding.searchCount.isVisible = false
        binding.placeholderMessage.isVisible = false
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY_MILLIS = 200L
    }
}