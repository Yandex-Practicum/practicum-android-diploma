package ru.practicum.android.diploma.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.search.R
import ru.practicum.android.diploma.search.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_placeFragment)
        }
        binding.workProfession.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_professionFragment)
        }
        binding.buttonLeftFilter.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
