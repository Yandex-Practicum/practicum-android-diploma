package ru.practicum.android.diploma.ui.favourite

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.presentation.favourite.FavouritesState
import ru.practicum.android.diploma.ui.search.adapter.VacancyAdapter

private const val ITEM_VIEW_WIDTH_DIVIDER = 3
private const val ITEM_VIEW_HEIGHT_DIVIDER = 2.8

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()
    private var vacanciesAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        vacanciesAdapter = VacancyAdapter {
            val navController = findNavController()
            val bundle = Bundle()
            Log.d("bundle", "$bundle")
            bundle.putParcelable("vacancyId", it)
            TODO("Navigation to details of vacancy")
            //navController.navigate(R.id.action_favoritesFragment_to_vacancyDetailsFragment, bundle)
        }
        binding.rvVacancies.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvVacancies.adapter = vacanciesAdapter

        val itemTouchHelper = ItemTouchHelper(getSwipeCallback())
        itemTouchHelper.attachToRecyclerView(binding.rvVacancies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vacanciesAdapter = null
    }

    private fun render(state: FavouritesState) {
        when (state) {
            is FavouritesState.Content -> renderContent(state)
            FavouritesState.DbError -> renderDbError()
            FavouritesState.Empty -> renderEmpty()
        }
    }

    private fun renderContent(state: FavouritesState) {
        vacanciesAdapter?.setItems((state as FavouritesState.Content).vacancies)
        binding.groupEmpty.visibility = View.GONE
        binding.groupError.visibility = View.GONE
        binding.rvVacancies.visibility = View.VISIBLE
    }

    private fun renderDbError() {
        binding.groupEmpty.visibility = View.GONE
        binding.groupError.visibility = View.VISIBLE
        binding.rvVacancies.visibility = View.GONE
    }

    private fun renderEmpty() {
        binding.groupEmpty.visibility = View.VISIBLE
        binding.groupError.visibility = View.GONE
        binding.rvVacancies.visibility = View.GONE
    }

    private fun getSwipeCallback(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val vacancyId = vacanciesAdapter?.getItemByPosition(viewHolder.adapterPosition)?.id
                if (!vacancyId.isNullOrBlank()) {
                    viewModel.deleteVacancyFromFavorite(vacancyId)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val trashBinIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_trashcan)

                c.clipRect(0f, viewHolder.itemView.top.toFloat(), dX, viewHolder.itemView.bottom.toFloat())

                if (dX < viewHolder.itemView.width / ITEM_VIEW_WIDTH_DIVIDER) {
                    c.drawColor(ContextCompat.getColor(requireContext(), R.color.fav_background_after_delete))
                } else {
                    c.drawColor(ContextCompat.getColor(requireContext(), R.color.light_red))
                }

                val listItemHeight = (viewHolder.itemView.height / ITEM_VIEW_HEIGHT_DIVIDER).toInt()

                if (trashBinIcon != null) {
                    trashBinIcon.bounds = Rect(
                        listItemHeight,
                        viewHolder.itemView.top + listItemHeight,
                        listItemHeight + trashBinIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                            + listItemHeight
                    )
                }

                trashBinIcon?.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
    }
}
