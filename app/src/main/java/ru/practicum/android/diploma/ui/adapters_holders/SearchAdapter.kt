package ru.practicum.android.diploma.ui.adapters_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemSearchListBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class SearchAdapter(
    private val callback: VacancyClickListener
) : RecyclerView.Adapter<VacancyViewHolder>() {
    private var vacancies: List<Vacancy> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding =
            ItemSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener { view ->
            callback.onClick(vacancies[position])
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    fun updateList(newList: List<Vacancy>) {
        val oldList = vacancies
        val difResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        })
        vacancies = newList.toList()
        difResult.dispatchUpdatesTo(this)
    }

    fun interface VacancyClickListener {
        fun onClick(vacancy: Vacancy)
    }

}
