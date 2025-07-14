package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    //Моковые данные для просмотра разметки
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dummyData = listOf(
            Vacancy("Android-разработчик", "Яндекс", "от 150 000 ₽", R.drawable.vacancy_logo_placeholder),
            Vacancy("Backend-инженер", "VK", "от 200 000 ₽", R.drawable.vacancy_logo_placeholder),
            Vacancy("DevOps", "Тинькофф", "от 180 000 ₽", R.drawable.vacancy_logo_placeholder)
        )

        val adapter = VacancyAdapter(dummyData)
        binding.recyclerViewFavouritesVacancies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavouritesVacancies.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
