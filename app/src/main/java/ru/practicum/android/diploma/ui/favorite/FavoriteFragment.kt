package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteFragment : Fragment() {

    private val viewModel by viewModel<FavoriteFragmentViewModel>()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val data = ArrayList<Vacancy>()
    private lateinit var recyclerView: RecyclerView

    private val vacancyClicked: (Vacancy) -> Unit = {
        // TODO("передача :Vacancy в фрагмент ДеталиВакансии")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.favoriteItemsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FavoriteFragmentRecyclerViewAdapter(data, vacancyClicked)

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is FavoriteFragmentUpdate.EmptyVacancyList -> {
                    binding.favoriteItemsRecyclerView.visibility = View.INVISIBLE
                    binding.emptyListFrame.visibility = View.VISIBLE
                    binding.getListErrorFrame.visibility = View.INVISIBLE
                }

                is FavoriteFragmentUpdate.GetVacanciesError -> {
                    binding.favoriteItemsRecyclerView.visibility = View.INVISIBLE
                    binding.emptyListFrame.visibility = View.INVISIBLE
                    binding.getListErrorFrame.visibility = View.VISIBLE
                }

                is FavoriteFragmentUpdate.VacancyList -> {
                    binding.favoriteItemsRecyclerView.visibility = View.VISIBLE
                    binding.emptyListFrame.visibility = View.INVISIBLE
                    binding.getListErrorFrame.visibility = View.INVISIBLE

                    data.clear()
                    data.addAll(it.vacancies)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
