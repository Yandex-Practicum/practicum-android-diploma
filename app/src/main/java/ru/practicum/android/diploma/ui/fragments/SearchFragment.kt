package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
