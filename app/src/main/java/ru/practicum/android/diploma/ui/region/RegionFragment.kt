package ru.practicum.android.diploma.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentRegionBinding
import ru.practicum.android.diploma.ui.country.RecyclerItem
import ru.practicum.android.diploma.ui.country.CountryAdapter
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegionFragment : Fragment() {
    private lateinit var binding: FragmentRegionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View    {
        binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }


        val regions = ArrayList<RecyclerItem>()
        regions.add(RecyclerItem("Регион 1"))
        regions.add(RecyclerItem("Регион 2"))
        regions.add(RecyclerItem("Регион 3"))
        regions.add(RecyclerItem("Регион 4"))
        regions.add(RecyclerItem("Регион 5"))
        regions.add(RecyclerItem("Регион 6"))
        regions.add(RecyclerItem("Регион 7"))
        regions.add(RecyclerItem("Регион 8"))
        regions.add(RecyclerItem("Регион 9"))
        regions.add(RecyclerItem("Регион 10"))
        regions.add(RecyclerItem("Регион 11"))
        regions.add(RecyclerItem("Регион 12"))
        regions.add(RecyclerItem("Регион 13"))

        val adapter = CountryAdapter(regions)
        binding.regionRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter
    }
}
