package ru.practicum.android.diploma.ui.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class PlaceFilterFragment : BindingFragment<FragmentPlaceFilterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaceFilterBinding {
        return FragmentPlaceFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // системная кн назад
        handleBackPress()

        // настройка текста для include items
        binding.countryItem.listLocationItem.text = getString(R.string.country_text)
        binding.regionItem.listLocationItem.text = getString(R.string.region_text)
        binding.selectedCountry.nameOfSelected.text = getString(R.string.country_text)
        binding.selectedRegion.nameOfSelected.text = getString(R.string.region_text)

        binding.selectedCountry.nameOfSelected.setOnClickListener {
            findNavController().navigate(
                R.id.action_placeFilterFragment_to_countryFilterFragment
            )
        }
    }
}
