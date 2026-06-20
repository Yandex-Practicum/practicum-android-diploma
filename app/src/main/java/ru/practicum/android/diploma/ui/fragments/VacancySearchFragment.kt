package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancySearchBinding
import ru.practicum.android.diploma.util.ViewStateHelper

class VacancySearchFragment : Fragment() {
    private var _binding: FragmentVacancySearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewStateHelper: ViewStateHelper

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
                binding.layoutLoading.root
            )
        )

        setupInitialState()
        setupNoInternetState()
        setupNoFoundState()

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
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }

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

    private fun showInitialState() {
        viewStateHelper.showOnly(binding.layoutInitial.root)
        binding.tvResultInfo.isVisible = false
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

    private fun showLoadingState() {
        viewStateHelper.showOnly(binding.layoutLoading.root)
        binding.tvResultInfo.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
