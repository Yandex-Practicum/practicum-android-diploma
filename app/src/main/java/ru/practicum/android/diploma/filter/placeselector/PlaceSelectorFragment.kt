package ru.practicum.android.diploma.filter.placeselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceSelectorBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_ID_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.REGION_KEY

class PlaceSelectorFragment : Fragment() {

    private var _binding: FragmentPlaceSelectorBinding? = null
    private val binding get() = _binding!!

    private var countryName: String = ""
    private var countryId: String = ""
    private var regionName: String = ""

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
        getData()
        initUi()
        initListeners()
    }

    private fun initListeners() {
        binding.filterToolbarPlace.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.countryNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_placeSelectorFragment_to_countryFragment)
        }
        binding.regionNavigation.setOnClickListener {
            val action = PlaceSelectorFragmentDirections.actionPlaceSelectorFragmentToRegionFragment(countryId, countryName)
            findNavController().navigate(action)
        }
    }

    private fun getData() {
        setFragmentResultListener(FILTER_RECEIVER_KEY) { _, bundle ->
            if (bundle.getString(REGION_KEY).toString().equals("null")) {
                regionName = ""
            } else {
                regionName = bundle.getString(REGION_KEY).toString()!!
            }
            countryName = bundle.getString(COUNTRY_KEY).toString()!!
            countryId = bundle.getString(COUNTRY_ID_KEY).toString()!!
            binding.countryText.setText(countryName)
            binding.regionText.setText(regionName)

            if (regionName.isEmpty() && countryName.isEmpty()) {
                binding.selectButton.visibility = View.GONE
            } else {
                binding.selectButton.visibility = View.VISIBLE
            }
        }
    }

    private fun initUi() {
        changeIcon(binding.countryText, binding.countryIcon)
        changeIcon(binding.regionText, binding.regionIcon)
        if (regionName.isEmpty() && countryName.isEmpty()) {
            binding.selectButton.visibility = View.GONE
        } else {
            binding.selectButton.visibility = View.VISIBLE
        }
        binding.selectButton.setOnClickListener {
            val action = PlaceSelectorFragmentDirections.actionPlaceSelectorFragmentToFilterFragment(
                binding.countryText.text.toString(),
                binding.regionText.text.toString()
            )
            findNavController().navigate(action)
        }
    }

    private fun changeIcon(editText: EditText, view: ImageView) {
        editText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                view.setImageResource(R.drawable.ic_arrow_forward)
            } else {
                view.setImageResource(R.drawable.ic_close)
                view.setOnClickListener {
                    editText.setText("")
                    changeIcon(editText, view)
                }
            }
        }
    }
}
