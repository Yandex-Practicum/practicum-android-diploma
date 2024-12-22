package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private var favoriteVacanciesRecyclerViewAdapter: VacancyAdapter? = null
    private val viewModel: FavoritesViewModel by viewModel()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onItemClickListener: (Vacancy) -> Unit = {
            // Логика, исполняемая по нажатию на элемент списка вакансий
        }
        val onItemLongClickListener: (Vacancy) -> Unit = {
            // Логика, исполняемая по длительному нажатию на элемент списка вакансий
        }
        favoriteVacanciesRecyclerViewAdapter = VacancyAdapter(
            onItemClicked = onItemClickListener,
            onLongItemClicked = onItemLongClickListener
        )
        binding.rvFavoriteVacancies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavoriteVacancies.adapter = favoriteVacanciesRecyclerViewAdapter

        viewModel.getFavoriteTracks()
        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { vacancies ->
            if (vacancies.isEmpty()) {
                // Обработка случая, когда список пуст
            } else {
                (favoriteVacanciesRecyclerViewAdapter as VacancyAdapter).updateVacancies(vacancies)
                binding.rvFavoriteVacancies.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
