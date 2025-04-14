package ru.practicum.android.diploma.presentation.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding

class FiltersFragment : Fragment() {

    private lateinit var binding: FragmentFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFiltersBinding.bind(view)

        binding.bAccept.setOnClickListener {
            val args = Bundle().apply { putString("filters", "some_filter_data") }
            findNavController().navigate(R.id.action_navigation_filters_to_navigation_main, args)
        }

        binding.bCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
