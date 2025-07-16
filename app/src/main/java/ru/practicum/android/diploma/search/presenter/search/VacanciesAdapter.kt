package ru.practicum.android.diploma.search.presenter.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.presenter.model.VacancyPreviewUi

class VacanciesAdapter(
    private val context: Context,
    private val vacanciesList: MutableList<VacancyPreviewUi>,
    private val onVacancyClick: (Int) -> Unit
) : RecyclerView.Adapter<VacanciesAdapter.VacanciesViewHolder>() {

    class VacanciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageId)
        val titleTextView: TextView = itemView.findViewById(R.id.titleId)
        val teamTextView: TextView = itemView.findViewById(R.id.teamId)
        val salaryTextView: TextView = itemView.findViewById(R.id.salaryId)
        val vacancyContainer: ConstraintLayout = itemView.findViewById(R.id.vacancyContainerId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacanciesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.vacancies_preview, parent, false)
        return VacanciesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vacanciesList.size
    }

    override fun onBindViewHolder(holder: VacanciesViewHolder, position: Int) {
        val vacancy = vacanciesList[position]
        Glide.with(context)
            .load(vacancy.logoUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

        holder.titleTextView.text = vacancy.name
        holder.teamTextView.text = vacancy.employerName
        holder.salaryTextView.text = vacancy.salary

        holder.vacancyContainer.setOnClickListener {
            onVacancyClick(vacancy.id)
        }
    }

    fun setList(newVacancies: List<VacancyPreviewUi>) {
        vacanciesList.clear()
        vacanciesList.addAll(newVacancies)
        notifyDataSetChanged()
    }

}
