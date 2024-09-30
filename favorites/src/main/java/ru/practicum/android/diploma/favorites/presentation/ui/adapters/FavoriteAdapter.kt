package ru.practicum.android.diploma.favorites.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.favorites.R
import ru.practicum.android.diploma.favorites.databinding.ItemFavoriteBinding
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

class FavoriteAdapter(
    private val isConnected: Boolean,
    private val favoriteVacancies: List<FavoriteVacancy>,
    private val favoriteClickListener: FavoriteClickListener
): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> () {
    private var vacancies = favoriteVacancies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(isConnected, ItemFavoriteBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener{
            favoriteClickListener.onFavoriteClick(vacancies[position])
        }
    }

    fun interface FavoriteClickListener {
        fun onFavoriteClick(favoriteVacancy: FavoriteVacancy)
    }

    class FavoriteViewHolder(private val isConnected: Boolean, private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(model: FavoriteVacancy) {
            if(isConnected) {
                Glide.with(itemView)
                    .load(model.urlLogo)
                    .placeholder(R.drawable.placeholder_logo_item_favorite)
                    .transform(CenterCrop(), RoundedCorners(Utils.doToPx(12f, itemView.context.applicationContext)))
                    .transform()
                    .into(binding.itemLogoVacancy)
            }
            binding.itemNameVacancyAndLocation.text = "${model.nameVacancy}, ${model.location}"
            binding.itemNameCompany.text = model.nameCompany
            binding.itemSalarySize.text = model.salary
        }
    }
}
