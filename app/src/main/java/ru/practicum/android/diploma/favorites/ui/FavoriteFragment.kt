package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoriteVacancyViewModel
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteVacancyViewModel by viewModel { parametersOf(requireContext()) }

    private val adapter = VacancyAdapter { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testVacancies = listOf(
            FavoriteVacancyEntity(
                id = "1",
                name = "Android-разработчик",
                employerName = "Яндекс",
                areaName = "Москва",
                descriptionHtml = "<p>Разработка мобильных приложений</p>",
                salaryFrom = 150_000,
                salaryTo = 200_000,
                currency = "RUR",
                experience = "От 1 года до 3 лет",
                employment = "Полная занятость",
                schedule = "Полный день",
                keySkills = "Kotlin,Android,Jetpack",
                logoUrl = "https://hh.ru/employer-logo/289027.png",
                url = "https://hh.ru/vacancy/1"
            ),
            FavoriteVacancyEntity(
                id = "2",
                name = "Backend-разработчик",
                employerName = "VK",
                areaName = "СПб",
                descriptionHtml = "<p>Разработка микросервисов</p>",
                salaryFrom = 180_000,
                salaryTo = 220_000,
                currency = "RUR",
                experience = "3–6 лет",
                employment = "Полная занятость",
                schedule = "Удалёнка",
                keySkills = "Kotlin,Spring,PostgreSQL",
                logoUrl = null,
                url = "https://hh.ru/vacancy/2"
            )
        )

        lifecycleScope.launch {
            testVacancies.forEach { viewModel.addToFavorites(it) }
        }

        binding.recyclerViewFavouritesVacancies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavouritesVacancies.adapter = adapter

        observeFavorites()

    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { list ->
                    if (list.isEmpty()) {
                        binding.recyclerViewFavouritesVacancies.visibility = View.GONE
                        binding.placeholderNothing.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewFavouritesVacancies.visibility = View.VISIBLE
                        binding.placeholderNothing.visibility = View.GONE
                        adapter.setItems(list)
                    }
                }
            }
        }
    }
}
