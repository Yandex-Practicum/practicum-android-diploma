package ru.practicum.android.diploma.ui.industry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.ui.country.CountryAdapter
import ru.practicum.android.diploma.ui.country.RecyclerItem


class IndustryFragment : Fragment() {

    private lateinit var binding: FragmentIndustryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View    {
        binding = FragmentIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        val industry = ArrayList<RecyclerItem>()


        industry.add(RecyclerItem("Отрасль 1"))
        industry.add(RecyclerItem("Отрасль 2"))
        industry.add(RecyclerItem("Отрасль 3"))
        industry.add(RecyclerItem("Отрасль 4"))
        industry.add(RecyclerItem("Отрасль 5"))
        industry.add(RecyclerItem("Отрасль 6"))
        industry.add(RecyclerItem("Отрасль 7"))
        industry.add(RecyclerItem("Отрасль 8"))
        industry.add(RecyclerItem("Отрасль 9"))
        industry.add(RecyclerItem("Отрасль 10"))
        industry.add(RecyclerItem("Отрасль 11"))
        industry.add(RecyclerItem("Отрасль 12"))
        industry.add(RecyclerItem("Отрасль 13"))

        val adapter = CountryAdapter(industry)
        binding.regionRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.regionRecycler.adapter = adapter
    }
}
