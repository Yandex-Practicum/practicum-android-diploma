package ru.practicum.android.diploma.ui.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity

class PlaceFilterFragment : BindingFragment<FragmentPlaceFilterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaceFilterBinding {
        return FragmentPlaceFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Системная кнопка или жест назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(false)
        }

        // настройка текста для include items
        textSetupForInclude()
        initTopbar()
        initListenersCountry()
        initListenersRegion()
    }

    private fun initTopbar() {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.place_of_work)
        }

        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(false)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()
    }

    private fun initListenersCountry() {
        binding.countryItem.apply {
            root.setOnClickListener {
                findNavController().navigate(R.id.action_placeFilterFragment_to_countryFilterFragment)
            }
        }
    }

    private fun initListenersRegion() {
        binding.regionItem.apply {
            root.setOnClickListener {
                findNavController().navigate(R.id.action_placeFilterFragment_to_regionFilterFragment)
            }
        }
    }

    private fun textSetupForInclude() {
        binding.countryItem.listLocationItem.text = getString(R.string.country_text)
        binding.regionItem.listLocationItem.text = getString(R.string.region_text)
        binding.selectedCountry.root.findViewById<TextView>(
            R.id.name_of_selected
        ).text = getString(R.string.country_text)
        binding.selectedRegion.root.findViewById<TextView>(
            R.id.name_of_selected
        ).text = getString(R.string.region_text)
    }
}
