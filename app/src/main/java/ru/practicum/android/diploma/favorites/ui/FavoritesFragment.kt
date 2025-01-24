package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteScreenViewModel
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import ru.practicum.android.diploma.search.ui.VacancyAdapter
import ru.practicum.android.diploma.search.ui.VacancyViewHolder

class FavoritesFragment : Fragment(), VacancyViewHolder.OnItemClickListener {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteScreenViewModel>()
    private var vacancyList: ArrayList<VacancyItems> = arrayListOf()
    private val vacancyAdapter = VacancyAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vacancyAdapter.onItemClickListener = this

        // для поиска
        val rvItems: RecyclerView = binding.rvFavoriteItems
        rvItems.apply {
            adapter = vacancyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        vacancyAdapter.items = vacancyList

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is LikeTracksScreenState.Content -> {
                    showContent(state.data)
                }

                is LikeTracksScreenState.Error -> {
                    showError(state.message)
                }
            }
        }
    }

  /*  private fun showError(code: String) {
        trackList.clear()
        trackAdapter.notifyDataSetChanged()

        val placeholderImage: ImageView = binding.errorImageLikeTracks
        val placeholderLayout: LinearLayout = binding.errorLayoutLikeTracks

        placeholderLayout.visibility = View.VISIBLE
        placeholderImage.setImageResource(R.drawable.nothing_found)

        var recyclerView: RecyclerView = binding.rvLikeItems
        recyclerView.visibility = View.GONE
    }

    private fun showContent(data: List<Track>) {
        val recyclerView: RecyclerView = binding.rvLikeItems
        recyclerView.apply {
            adapter = trackAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        trackList.clear()

        trackList.addAll(data)
        trackAdapter.items = trackList
        trackAdapter.notifyDataSetChanged()

        val placeholderLayout: LinearLayout = binding.errorLayoutLikeTracks
        placeholderLayout.visibility = View.GONE

        recyclerView.visibility = View.VISIBLE
    }
     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: VacancyItems) {
       /* val direction: NavDirections =
            MediatekaFragmentDirections.actionMediatekaFragmentToMediaFragment(item)
        findNavController().navigate(direction)*/
    }

}
