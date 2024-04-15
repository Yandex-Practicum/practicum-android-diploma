package ru.practicum.android.diploma.ui.search.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyLoadStateBinding
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class VacancyAdapter(
    private val onClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currentList = mutableListOf<Vacancy?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_VACANCY -> {
                val binding = VacancyViewBinding.inflate(inflater, parent, false)
                VacancyViewHolder(binding)
            }

            TYPE_LOADER -> {
                val binding = VacancyLoadStateBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }

            else -> {
                throw RuntimeException("There is no type that matches the type $viewType + make sure your using types correctly")
            }
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VacancyViewHolder -> currentList[position]?.let { holder.bind(it, onClick) }
            is LoadingViewHolder -> {}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            currentList[position] != null -> TYPE_VACANCY
            else -> TYPE_LOADER
        }
    }

    fun updateVacancies(vacancies: List<Vacancy>) {
        currentList.clear()
        currentList.addAll(vacancies)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        currentList.add(null)
        notifyItemInserted(currentList.size - 1)
    }

    fun removeLoadingView() {
        if (currentList.isNotEmpty()) {
            currentList.removeAt(itemCount - 1)
            notifyItemRemoved(currentList.size)
        }
    }

    fun clearList() {
        val size = currentList.size
        currentList.clear()
        notifyItemRangeRemoved(0, size)
    }

    companion object {
        const val TYPE_VACANCY = 1
        const val TYPE_LOADER = 0
    }
}
