package ru.practicum.android.diploma.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_search,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSearchBinding.bind(view)

        binding.toFiltersButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_main_to_navigation_filters)
        }

        binding.toVacancyButton.setOnClickListener {
            val args = Bundle().apply { putString("vacancyId", "123") }
            findNavController().navigate(R.id.action_navigation_main_to_navigation_vacancy, args)
        }
    }
}
