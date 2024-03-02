package ru.practicum.android.diploma.filter.placeselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceSelectorBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.REGION_KEY

class PlaceSelectorFragment : Fragment() {

    private var _binding: FragmentPlaceSelectorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.country.setOnClickListener {
            findNavController().navigate(R.id.action_placeSelectorFragment_to_countryFragment)
        }
        binding.region.setOnClickListener {
            findNavController().navigate(R.id.action_placeSelectorFragment_to_regionFragment)
        }
        setFragmentResultListener(FILTER_RECEIVER_KEY) { requestKey, bundle ->
            val country = bundle.getString(COUNTRY_KEY)
            val region = bundle.getString(REGION_KEY)
        }
    }
}
