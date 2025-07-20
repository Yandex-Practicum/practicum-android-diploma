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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_VACANCY = 0
        private const val TYPE_LOADING = 1
    }

    private var isLoading = false

    override fun getItemViewType(position: Int): Int {
        return if (isLoading && position == vacanciesList.size) TYPE_LOADING else TYPE_VACANCY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_VACANCY) {
            val view = LayoutInflater.from(context).inflate(R.layout.vacancies_preview, parent, false)
            VacanciesViewHolder(view, onVacancyClick)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return vacanciesList.size + if (isLoading) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacanciesViewHolder && position < vacanciesList.size) {
            val vacancy = vacanciesList[position]
            holder.bind(vacancy)
        }
    }

    fun setList(newVacancies: List<VacancyPreviewUi>) {
        vacanciesList.clear()
        vacanciesList.addAll(newVacancies)
        notifyDataSetChanged()
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            notifyItemInserted(vacanciesList.size)
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            notifyItemRemoved(vacanciesList.size)
        }
    }

    class VacanciesViewHolder(
        itemView: View,
        private val onVacancyClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageId)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleId)
        private val teamTextView: TextView = itemView.findViewById(R.id.teamId)
        private val salaryTextView: TextView = itemView.findViewById(R.id.salaryId)
        private val vacancyContainer: ConstraintLayout = itemView.findViewById(R.id.vacancyContainerId)

        fun bind(vacancy: VacancyPreviewUi) {
            Glide.with(itemView.context)
                .load(vacancy.logoUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)

            titleTextView.text = vacancy.name
            teamTextView.text = vacancy.employerName
            salaryTextView.text = vacancy.salary

            vacancyContainer.setOnClickListener {
                onVacancyClick(vacancy.id)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
