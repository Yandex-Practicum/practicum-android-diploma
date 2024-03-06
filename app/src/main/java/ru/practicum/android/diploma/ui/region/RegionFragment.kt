package ru.practicum.android.diploma.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.ui.country.CountryAdapter

class RegionFragment : Fragment() {
    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

//        val regions = ArrayList<RecyclerItem>()
//        regions.add(RecyclerItem("Регион 1"))
//        regions.add(RecyclerItem("Регион 2"))
//        regions.add(RecyclerItem("Регион 3"))
//        regions.add(RecyclerItem("Регион 4"))
//        regions.add(RecyclerItem("Регион 5"))
//        regions.add(RecyclerItem("Регион 6"))
//        regions.add(RecyclerItem("Регион 7"))
//        regions.add(RecyclerItem("Регион 8"))
//        regions.add(RecyclerItem("Регион 9"))
//        regions.add(RecyclerItem("Регион 10"))
//        regions.add(RecyclerItem("Регион 11"))
//        regions.add(RecyclerItem("Регион 12"))
//        regions.add(RecyclerItem("Регион 13"))

        val adapter = CountryAdapter()
        adapter.itemClickListener = { _, item ->
            val bundle = Bundle()
            bundle.putString("keyRegion", item.name)
            setFragmentResult("requestKeyRegion", bundle)
            findNavController().popBackStack()
        }

        binding.regionRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter

        adapter.countryList.addAll(
            arrayListOf(
                Country("7584", "113", "Регион 1"),
                Country("7332", "112", "Регион 2"),
                Country("7123", "111", "Регион 3"),
                Country("2134", "110", "Регион 4"),
                Country("1231", "114", "Регион 5"),
                Country("7612", "115", "Регион 6"),
                Country("7543", "116", "Регион 7"),
                Country("7213", "123", "Регион 8"),
                Country("2211", "119", "Регион 9"),
                Country("2211", "119", "Регион 10"),
                Country("2211", "119", "Регион 11"),
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
