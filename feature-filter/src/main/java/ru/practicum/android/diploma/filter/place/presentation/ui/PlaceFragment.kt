package ru.practicum.android.diploma.filter.place.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentPlaceBinding

internal class PlaceFragment : Fragment() {
    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, arguments?.getString(ARGS_COUNTRY_NAME) ?: "")
        arguments?.getString(ARGS_COUNTRY_NAME)?.let {
            binding.tvCountry.text = it
        }
        binding.country.setOnClickListener {
            findNavController().navigate(R.id.action_placeFragment_to_countryFragment)
        }
        binding.region.setOnClickListener {
            findNavController().navigate(R.id.action_placeFragment_to_regionFragment)
        }
        binding.buttonLeftPlace.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "PlaceFragment"
        private const val ARGS_COUNTRY_ID = "country_id"
        private const val ARGS_COUNTRY_NAME = "country_name"

        fun createArgs(countryId: String, countryName: String): Bundle =
            bundleOf(ARGS_COUNTRY_ID to countryId, ARGS_COUNTRY_NAME to countryName)

    }
}
