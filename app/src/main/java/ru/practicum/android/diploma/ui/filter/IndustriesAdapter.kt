package ru.practicum.android.diploma.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesAdapter: RecyclerView.Adapter<IndustryViewHolder>() {
    var data: List<Industry> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(ListOfIndustryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Industry>){
        data = list
        notifyDataSetChanged()
    }
}
