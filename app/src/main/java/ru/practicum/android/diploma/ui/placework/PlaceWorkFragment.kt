package ru.practicum.android.diploma.ui.placework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceWorkBinding
import ru.practicum.android.diploma.presentation.placework.PlaceWorkViewModel

class PlaceWorkFragment : Fragment() {

    private var _binding: FragmentPlaceWorkBinding? = null
    private val binding: FragmentPlaceWorkBinding get() = _binding!!
    private val viewModel: PlaceWorkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarPlaceWork.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.choiceCountry.setOnClickListener {
            findNavController().navigate(R.id.action_to_country_fragment)
        }

        binding.choiceRegion.setOnClickListener {
            findNavController().navigate(R.id.action_to_region_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
