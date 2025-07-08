package ru.practicum.android.diploma.ui.vacancy_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding

class VacancySearchFragment : Fragment() {

    private var binding: VacancySearchFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.details?.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_vacancyDetailsFragment)
        }
        binding?.filter?.setOnClickListener {
            findNavController().navigate(R.id.action_vacancySearchFragment_to_searchFiltersFragment)
        }
    }
}
