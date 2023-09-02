package ru.practicum.android.diploma.favourite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.BindingFragment
import ru.practicum.android.diploma.util.adapter.VacancyAdapter

class FragmentFavourite : BindingFragment<FragmentFavouriteBinding>() {

    lateinit var vacancyAdapter: VacancyAdapter
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        setListeners()
    }

    private fun initAdapter() {
        vacancyAdapter = VacancyAdapter(ArrayList<Vacancy>())
        binding.recyclerView.adapter = vacancyAdapter
    }

    private fun setListeners() {
        vacancyAdapter.itemClickListener = {position, vacancy ->

        }
    }
}