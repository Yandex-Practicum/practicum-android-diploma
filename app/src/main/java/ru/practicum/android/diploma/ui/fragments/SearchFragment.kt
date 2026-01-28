package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var hasSearched = false

    private val searchVacanciesInteractor: SearchVacanciesInteractor by inject()

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

        binding.includeToolbar.toolbar.contentInsetStartWithNavigation =
            resources.getDimensionPixelSize(R.dimen.indent_16)
        binding.includeToolbar.toolbar.title = getString(R.string.search_vacancies)

        binding.includeToolbar.btnBack.visibility = View.GONE

        setupUI()

        binding.buttonFilter.setOnClickListener {
            findNavController().navigate(R.id.action_search_to_filter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchVacanciesInteractor.searchVacancies("developer").collect { resource ->
                when (resource) {
                    is SearchVacanciesInteractor.Resource.Success -> {
                        binding.testTextView.text = "Found ${resource.data.size} vacancies"
                    }
                    is SearchVacanciesInteractor.Resource.Error -> {
                        binding.testTextView.text = "Error: ${resource.errorCode}"
                    }
                }
            }
        }
    }

    private fun setupUI() {
        updatePlaceholderVisibility()

        binding.searchVacancy.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Intentionally empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateClearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // Intentionally empty
            }
        })

        binding.buttonSearchClear.setOnClickListener {
            if (binding.searchVacancy.text.isNotEmpty()) {
                binding.searchVacancy.text.clear()
                hasSearched = false
                updatePlaceholderVisibility()
            }
        }
    }

    private fun updateClearButtonVisibility(text: CharSequence?) {
        if (!text.isNullOrEmpty()) {
            binding.buttonSearchClear.setImageResource(R.drawable.ic_clear)
        } else {
            binding.buttonSearchClear.setImageResource(R.drawable.ic_search_24)
            if (hasSearched) {
                hasSearched = false
                updatePlaceholderVisibility()
            }
        }
    }

    private fun updatePlaceholderVisibility() {
        if (!hasSearched) {
            binding.placeholderSearch.setImageResource(R.drawable.img_empty_search)
            binding.placeholderSearch.visibility = View.VISIBLE
            binding.textImageCaption.text = getString(R.string.enter_query)
            binding.textImageCaption.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.resultSearchInformation.visibility = View.GONE
        } else {
            binding.placeholderSearch.visibility = View.GONE
            binding.textImageCaption.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
