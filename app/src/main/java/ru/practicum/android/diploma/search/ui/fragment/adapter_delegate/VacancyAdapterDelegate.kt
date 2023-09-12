package ru.practicum.android.diploma.search.ui.fragment.adapter_delegate

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.setImage

class VacancyAdapterDelegate(
    private val onClick: (Vacancy) -> Unit,
    private val onLongClick: ((Vacancy) -> Unit)? = null,
) : DelegateAdapter<Vacancy, VacancyAdapterDelegate.VacancyViewHolder>(Vacancy::class.java) {
    
    private var count = 0
    
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("BannerAdapterDelegate", "createViewHolder: _____ ${++count}")
        return VacancyViewHolder(binding)
    }
    
    override fun bindViewHolder(
        model: Vacancy,
        viewHolder: VacancyViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.itemView.setOnClickListener {
            onClick(model)
        }
        
        viewHolder.itemView.setOnLongClickListener {
            onLongClick?.let { onLongClick -> onLongClick(model) }
            true
        }
        
        viewHolder.bind(model)
    }

    inner class VacancyViewHolder(private val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vacancy) {

            val titleAndArea: String =
                if (item.area.isNotEmpty()) "${item.title}, ${item.area}"
                else item.title

            val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.size_12dp)
            binding.title.text = titleAndArea
            binding.company.text = item.company
            binding.value.text = item.salary
            binding.image.setImage(
                url = item.iconUri,
                placeholder = R.drawable.ic_placeholder_company,
                cornerRadius = cornerRadius
            )
        }
    }
}