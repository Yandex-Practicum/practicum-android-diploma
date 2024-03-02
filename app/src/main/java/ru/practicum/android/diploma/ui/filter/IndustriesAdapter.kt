package ru.practicum.android.diploma.ui.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.response.IndustryResponse
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesAdapter(val onCLick: (Int, String) -> Unit): RecyclerView.Adapter<IndustryViewHolder>() {
    var data: List<Industry> = emptyList()
    var checkedRadioButtonId: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(ListOfIndustryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.binding.radioButton.isChecked = checkedRadioButtonId == position
        holder.binding.root.setOnClickListener {
            checkedRadioButtonId = position
            onCLick.invoke(checkedRadioButtonId, data[checkedRadioButtonId].name)
            notifyDataSetChanged()
        }
        holder.binding.radioButton.setOnClickListener {
            checkedRadioButtonId = position
            onCLick.invoke(checkedRadioButtonId, data[checkedRadioButtonId].name)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Industry>){
        data = list
        notifyDataSetChanged()
    }
}
