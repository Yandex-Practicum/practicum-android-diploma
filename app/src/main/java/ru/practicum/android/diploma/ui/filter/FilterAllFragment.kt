package ru.practicum.android.diploma.ui.filter

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAllFilterBinding

class FilterAllFragment : Fragment() {

    private var _binding: FragmentAllFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterWorkplace.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_workplaceFragment)
        }

        binding.filterIndustries.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_industriesFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
