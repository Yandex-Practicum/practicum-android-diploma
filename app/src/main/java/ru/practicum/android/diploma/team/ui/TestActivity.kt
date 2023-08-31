package ru.practicum.android.diploma.team.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R

class TestActivity : AppCompatActivity() {
    private lateinit var mData: ArrayList<Int>
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_team2)
        mData = ArrayList()
        val swipeStack = findViewById<SwipeStack>(R.id.swipeStack)
        swipeStack.adapter = SwipeAdapter(mData)
        getImgData()
    }

    private fun getImgData() {
        mData.add(R.drawable.rectangle1)
        mData.add(R.drawable.rectangle2)
        mData.add(R.drawable.rectangle3)
        mData.add(R.drawable.rectangle4)
        mData.add(R.drawable.rectangle5)
        mData.add(R.drawable.rectangle6)
    }

}