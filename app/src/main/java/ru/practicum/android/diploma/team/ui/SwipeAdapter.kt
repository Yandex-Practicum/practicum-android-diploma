package ru.practicum.android.diploma.team.ui

import android.annotation.SuppressLint
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import ru.practicum.android.diploma.R

class SwipeAdapter(
    private val mData: List<Int>
) : BaseAdapter() {
    override fun getCount(): Int {
        Log.e("MyLog", "SwipeAdapter -> getCount(): Int")
        return mData.size
    }

    override fun getItem(position: Int): Any {
        Log.e("MyLog", "SwipeAdapter -> getItem(p0: Int): Any")
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        Log.e("MyLog", "SwipeAdapter -> getItemId(p0: Int): Long")
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View, parent: ViewGroup?): View {
        Log.e("MyLog", "SwipeAdapter -> getView(p0: Int, p1: View?, p2: ViewGroup?): View")
        var convertView: View = convertView
        convertView = LayoutInflater.from(parent!!.context).inflate(R.layout.card_items, parent, false)
        val imageViewCard = convertView.findViewById(R.id.imgViewCard) as ImageView
        imageViewCard.setImageResource(mData[position])
        return convertView
    }
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val inflater = parent?.context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val cardView = convertView ?: inflater.inflate(R.layout.card_items, parent, false)
//        val imageView = cardView.findViewById<ImageView>(R.id.imageView)
//        imageView.setImageResource(mData[position])
//
//        return cardView
//    }
}