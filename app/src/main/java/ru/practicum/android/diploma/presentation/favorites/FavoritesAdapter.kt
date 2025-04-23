package ru.practicum.android.diploma.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ElementVacancyShortBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort
import ru.practicum.android.diploma.util.extensions.toFormattedString

class FavoritesAdapter(
    private val onClick: (VacancyShort) -> Unit,
) : ListAdapter<VacancyShort, FavoritesAdapter.VacancyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementVacancyShortBinding.inflate(inflater, parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VacancyViewHolder(private val binding: ElementVacancyShortBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VacancyShort) = with(binding) {
            binding.textJobNameAndCity.text = "${item.name}, ${item.area}"
            binding.textEmployerName.text = item.employer
            binding.textSalary.text = item.salary.toFormattedString(root.context)
            Glide.with(itemView.context)
                .load(item.logoUrl?.logo90)
                .placeholder(R.drawable.ic_placeholder)
                .transform(
                    FitCenter(),
                    RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.radius_12))
                )
                .into(binding.imageEmployer)
            root.setOnClickListener { onClick(item) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<VacancyShort>() {
            override fun areItemsTheSame(oldItem: VacancyShort, newItem: VacancyShort) =
                oldItem.vacancyId == newItem.vacancyId

            override fun areContentsTheSame(oldItem: VacancyShort, newItem: VacancyShort) =
                oldItem == newItem
        }
    }
}
