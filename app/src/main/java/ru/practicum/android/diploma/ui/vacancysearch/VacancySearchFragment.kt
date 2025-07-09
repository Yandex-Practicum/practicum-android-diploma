package ru.practicum.android.diploma.ui.vacancysearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding

class VacancySearchFragment : Fragment() {

    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.toolbarTitle.text = getString(R.string.search_vacancy)

        binding.details.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_vacancyDetailsFragment)
        }
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
