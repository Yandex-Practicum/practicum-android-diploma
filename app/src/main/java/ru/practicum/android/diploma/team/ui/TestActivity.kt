package ru.practicum.android.diploma.team.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R

class TestActivity : AppCompatActivity(), SwipeStack.SwipeStackListener {
    private lateinit var mData: ArrayList<Int>
    private var mAdapter: SwipeAdapter? = null
    private var mSwipeStack: SwipeStack? = null
    //private val mAdapter by lazy { SwipeAdapter(getImgData()) }
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_team2)
        mSwipeStack = findViewById<SwipeStack>(R.id.swipeStack) as SwipeStack
        mData = ArrayList()
        //val swipeStack = findViewById<SwipeStack>(R.id.swipeStack)
//        swipeStack.adapter = mAdapter

        mAdapter = SwipeAdapter(mData)
        mSwipeStack!!.adapter = mAdapter
        mSwipeStack!!.setListener(this)
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

    override fun onViewSwipedToLeft(position: Int) {
        val swipedElement = mAdapter!!.getItem(position)
        Toast.makeText(
            this, "view_swiped_left, $swipedElement)",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onViewSwipedToRight(position: Int) {
        val swipedElement = mAdapter!!.getItem(position)
        Toast.makeText(
            this, "view_swiped_right, $swipedElement)",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStackEmpty() {
        Toast.makeText(this, "R.string.stack_empty", Toast.LENGTH_SHORT).show()
    }

}