package ru.practicum.android.diploma.ui.favorites.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyListItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private var vacancies: List<Vacancy> = emptyList(),
    private val onItemClicked: (Vacancy) -> Unit,
) : RecyclerView.Adapter<VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyListItemBinding.inflate(layoutInflater, parent, false)) {
            onItemClicked(vacancies[it])
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listOfVacancies: List<Vacancy>) {
        vacancies = listOfVacancies
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllData() {
        vacancies = emptyList()
        notifyDataSetChanged()
    }
}
