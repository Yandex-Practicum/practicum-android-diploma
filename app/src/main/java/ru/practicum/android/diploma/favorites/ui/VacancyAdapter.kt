package ru.practicum.android.diploma.favorites.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.CardVacancyInfoBinding
import ru.practicum.android.diploma.favorites.ui.model.VacancyUiModel

class VacancyAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    private val vacancies = mutableListOf<VacancyUiModel>()

    fun setItems(newItems: List<VacancyUiModel>) {
        vacancies.clear()
        vacancies.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val binding = CardVacancyInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VacancyViewHolder(binding) { position ->
            onItemClick(vacancies[position].id)
        }
    }

    override fun getItemCount() = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    class VacancyViewHolder(
        private val binding: CardVacancyInfoBinding,
        onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(vacancy: VacancyUiModel) {
            binding.vacancyName.text = vacancy.name
            binding.companyName.text = vacancy.employer
            binding.companySalary.text = vacancy.salary

            val context = binding.root.context
            val placeholder = R.drawable.vacancy_logo_placeholder

            Glide.with(context)
                .load(vacancy.logoUrl ?: "")
                .placeholder(placeholder)
                .error(placeholder)
                .centerCrop()
                .into(binding.companyLogo)
        }
    }
}
