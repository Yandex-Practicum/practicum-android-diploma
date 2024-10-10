package ru.practicum.android.diploma.favorites.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.ui.databinding.ItemVacancyBinding

private const val RADIUS_ROUND_VIEW = 12f

class FavoriteAdapter(
    private val favoriteVacancies: List<FavoriteVacancy>,
    private val favoriteClickListener: FavoriteClickListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var vacancies = favoriteVacancies

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newFavorites: List<FavoriteVacancy>) {
        vacancies = newFavorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(ItemVacancyBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            favoriteClickListener.onFavoriteClick(vacancies[position])
        }
    }

    fun interface FavoriteClickListener {
        fun onFavoriteClick(favoriteVacancy: FavoriteVacancy)
    }

    class FavoriteViewHolder(
        private val binding: ItemVacancyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: FavoriteVacancy) {
            Glide.with(itemView)
                .load(model.urlLogo)
                .placeholder(R.drawable.placeholder_logo_item_favorite)
                .centerCrop()
                .transform(
                    RoundedCorners(
                        Utils.doToPx(
                            RADIUS_ROUND_VIEW,
                            itemView.context.applicationContext
                        )
                    )
                )
                .transform()
                .into(binding.itemLogoVacancy)
            binding.itemNameVacancyAndLocation.text = "${model.nameVacancy}, ${model.location}"
            binding.itemNameCompany.text = model.nameCompany
            binding.itemSalarySize.text = model.salary
        }
    }
}
