package ru.practicum.android.diploma.filter.place.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.databinding.ItemPlaceBinding

class PlacesAdapter<T>(
    private val placeClickListener: PlaceClickListener<T>,
    private val itemBinder: (binding: ItemPlaceBinding, item: T) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder<T>>() {

    private val places: MutableList<T> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updatePlaces(list: List<T>) {
        places.clear()
        places.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(ItemPlaceBinding.inflate(layoutInflater, parent, false), placeClickListener, itemBinder)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: PlacesViewHolder<T>, position: Int) {
        holder.bind(places[position])
    }

    fun interface PlaceClickListener<T> {
        fun onPlaceClick(id: T)
    }

    class PlacesViewHolder<T>(
        private val binding: ItemPlaceBinding,
        private val placeClickListener: PlaceClickListener<T>,
        private val itemBinder: (binding: ItemPlaceBinding, item: T) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            itemBinder(binding, item)
            binding.root.setOnClickListener {
                placeClickListener.onPlaceClick(item)
            }
        }
    }
}
