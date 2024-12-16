package ru.practicum.android.diploma.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyListItemBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    private var vacancies: List<Vacancy> = emptyList(),
    private val onItemClicked: (Vacancy) -> Unit,
    private val onLongItemClicked: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyListItemBinding.inflate(layoutInspector, parent, false)) {
            onItemClicked(vacancies[it])
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnLongClickListener {
            onLongItemClicked(vacancies[position])
            true
        }
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

    class VacancyViewHolder(
        private val binding: VacancyListItemBinding,
        onItemClicked: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(model: Vacancy) {
            //TODO("Добавить корректное отображение для всех полей элемента списка после исправления класса Vacancy")
            Glide.with(binding.root)
                .load(R.drawable.company_logo_placeholder)
                .placeholder(R.drawable.company_logo_placeholder)
                .centerCrop()
                .into(binding.ivCompanyLogo)

            binding.tvTitleOfVacancy.text = "Какая-то вакансия"
            binding.tvCompanyName.text = "Какое-то название компании"
            binding.tvVacancySalary.text = "от 100500 тысяч"
        }

    }

}
