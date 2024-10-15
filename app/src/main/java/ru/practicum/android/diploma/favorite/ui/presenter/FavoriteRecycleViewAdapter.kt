package ru.practicum.android.diploma.favorite.ui.presenter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyCardBinding
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.ClickListener

class FavoriteRecycleViewAdapter(
    private val clickListener: ClickListener
) :
    RecyclerView.Adapter<FavoriteVacancyViewHolder>() {

    val vacancyList: MutableList<VacancySearch> = mutableListOf()
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVacancyViewHolder {
        val view = LayoutInflater.from(parent.context)
        return FavoriteVacancyViewHolder(VacancyCardBinding.inflate(view, parent, false))
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    private fun clickDebounce(): Boolean {
        val currentState = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return currentState
    }

    override fun onBindViewHolder(holder: FavoriteVacancyViewHolder, position: Int) {
        val itemView = vacancyList[position]
        holder.bind(itemView)

        holder.itemView.setOnClickListener {
            if (clickDebounce()) {
                clickListener.onClick(itemView)
            }
        }
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
