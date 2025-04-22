package ru.practicum.android.diploma.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.databinding.ElementVacancyShortBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.util.extensions.toFormattedString

class VacancyAdapter(
    private var vacancyList: List<VacancyShort> = emptyList(),
    private val onItemClickListener: (VacancyShort) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val vacancyItemBinding = ElementVacancyShortBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(vacancyItemBinding, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }

    fun updateVacancies(newVacancies: List<VacancyShort>) {
        vacancyList = newVacancies
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ElementVacancyShortBinding,
        private val onItemClickListener: (VacancyShort) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VacancyShort) {
            Glide.with(binding.imageEmployer.context)
                .load(item.logoUrl?.logo90)
                .into(binding.imageEmployer)
            binding.textJobNameAndCity.text = item.name
            binding.textEmployerName.text = item.employer
            binding.textSalary.text = item.salary.toFormattedString(itemView.context, false)
            itemView.setOnClickListener { onItemClickListener.invoke(item) }
        }
    }
}
