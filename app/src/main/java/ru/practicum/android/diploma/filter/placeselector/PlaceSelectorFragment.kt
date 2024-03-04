package ru.practicum.android.diploma.filter.placeselector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceSelectorBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.COUNTRY_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.REGION_KEY

class PlaceSelectorFragment : Fragment() {

    private var _binding: FragmentPlaceSelectorBinding? = null
    private val binding get() = _binding!!
    var country: String = ""
    var region: String = ""

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
            findNavController().navigate(R.id.action_placeSelectorFragment_to_regionFragment)
        }
        clearInput(binding.countryText, binding.countryIcon)
        clearInput(binding.regionText, binding.regionIcon)
    }

    private fun getData() {
        setFragmentResultListener(FILTER_RECEIVER_KEY) { requestKey, bundle ->
            country = bundle.getString(COUNTRY_KEY).toString()
            region = bundle.getString(REGION_KEY).toString()
        }
    }

    private fun initUi() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            binding.countryText.setText(country)
            binding.regionText.setText(region)
        }
        changeIcon(binding.countryText, binding.countryIcon)
        changeIcon(binding.regionText, binding.regionIcon)
        if (binding.countryText.text.isNullOrEmpty() && binding.regionText.text.isNullOrEmpty()) {
            binding.selectButton.visibility = View.GONE
        } else {
            binding.selectButton.visibility = View.VISIBLE
        }
        binding.selectButton.setOnClickListener {
            val action = PlaceSelectorFragmentDirections.actionPlaceSelectorFragmentToFilterFragment(country, region)
            findNavController().navigate(action)
        }
    }

    private fun changeIcon(editText: EditText, view: ImageView) {
        if (editText.text.isEmpty()) {
            view.setImageResource(R.drawable.ic_arrow_forward)
        } else {
            view.setImageResource(R.drawable.ic_close)
        }
    }

    private fun clearInput(editText: EditText, view: ImageView) {
        if (!editText.text.isNullOrEmpty()) {
            view.setOnClickListener {
                editText.setText("")
                changeIcon(editText, view)
            }
        }
    }
}
