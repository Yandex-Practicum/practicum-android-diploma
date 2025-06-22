package ru.practicum.android.diploma.ui.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.databinding.FragmentPlaceFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class PlaceFilterFragment : BindingFragment<FragmentPlaceFilterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaceFilterBinding {
        return FragmentPlaceFilterBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // системная кн назад
        handleBackPress()

        //настройка текста для include items
        binding.countryItem.listLocationItem.text = getString(R.string.country_text)
        binding.regionItem.listLocationItem.text = getString(R.string.region_text)
        binding.selectedCountry.selectedItem.findViewById<TextView>(R.id.name_of_selected).text = getString(R.string.country_text)
        binding.selectedRegion.selectedItem.findViewById<TextView>(R.id.name_of_selected).text = getString(R.string.region_text)
    }
}
