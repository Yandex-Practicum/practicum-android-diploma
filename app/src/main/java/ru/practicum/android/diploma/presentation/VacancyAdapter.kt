package ru.practicum.android.diploma.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ElementVacancyShortBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.util.extensions.toFormattedString

class VacancyAdapter(
    private val onItemClickListener: (VacancyShort) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vacancyList: List<VacancyShort> = emptyList()
    private var showFooterItem: Boolean = false
    private var showHeaderLoadingItem: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_VACANCY -> {
                val vacancyItemBinding = ElementVacancyShortBinding.inflate(layoutInflater, parent, false)
                VacancyViewHolder(vacancyItemBinding, onItemClickListener)
            }

            VIEW_TYPE_LOADING -> {
                val view = layoutInflater.inflate(R.layout.placeholder_loading_bottom, parent, false)
                LoadingViewHolder(view)
            }

            VIEW_TYPE_HEADER_LOADING -> {
                val view = layoutInflater.inflate(R.layout.placeholder_loading_bottom, parent, false)
                HeaderLoadingViewHolder(view)
            }

            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            showHeaderLoadingItem && position == 0 -> VIEW_TYPE_HEADER_LOADING
            position >= (if (showHeaderLoadingItem) vacancyList.size + 1 else vacancyList.size) -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_VACANCY
        }
    }

    override fun getItemCount(): Int {
        val header = if (showHeaderLoadingItem) 1 else 0
        val footer = if (showFooterItem) 1 else 0
        return header + vacancyList.size + footer
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VacancyViewHolder && position < vacancyList.size) {
            holder.bind(vacancyList[position])
        }
    }

    fun updateVacancies(
        newVacancies: List<VacancyShort>,
        showFooterLoading: Boolean,
        showHeaderLoading: Boolean = false
    ) {
        vacancyList = newVacancies
        showHeaderLoadingItem = showHeaderLoading
        showFooterItem = showFooterLoading
        notifyDataSetChanged()
        Log.d("Adapter", "header=$showHeaderLoading, footer=$showFooterLoading, size=${newVacancies.size}")
    }

    class VacancyViewHolder(
        private val binding: ElementVacancyShortBinding,
        private val onItemClickListener: (VacancyShort) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VacancyShort) {
            Glide.with(itemView.context)
                .load(item.logoUrl?.logo90)
                .placeholder(R.drawable.ic_placeholder)
                .transform(
                    FitCenter(),
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.radius_12))
                )
                .into(binding.imageEmployer)
            binding.textJobNameAndCity.text = "${item.name}, ${item.area}"
            binding.textEmployerName.text = item.employer
            binding.textSalary.text = item.salary.toFormattedString(itemView.context, false)
            itemView.setOnClickListener { onItemClickListener.invoke(item) }
        }
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class HeaderLoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val VIEW_TYPE_VACANCY = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_HEADER_LOADING = 2
    }
}
