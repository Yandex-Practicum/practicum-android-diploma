package ru.practicum.android.diploma.filter.placeselector.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentCountryBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY

class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterToolbarCountry.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val country = "Результат, который нужно отправить"
        val countryBundle = Bundle().apply {
            putString(COUNTRY_KEY, country)
        }
        setFragmentResult(FILTER_RECEIVER_KEY, countryBundle)
    }
}
