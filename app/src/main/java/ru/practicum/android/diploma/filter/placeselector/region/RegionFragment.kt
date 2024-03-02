package ru.practicum.android.diploma.filter.placeselector.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.REGION_KEY

class RegionFragment : Fragment() {

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterToolbarRegion.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val region = "Результат, который нужно отправить"
        val regionBundle = Bundle().apply {
            putString(REGION_KEY, region)
        }
        setFragmentResult(FILTER_RECEIVER_KEY, regionBundle)
    }

}
