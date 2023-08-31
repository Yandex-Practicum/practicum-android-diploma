package ru.practicum.android.diploma.team.ui

import android.content.Context
import android.database.DataSetObserver
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.FrameLayout
import org.jetbrains.annotations.Nullable
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.getParcelableFromBundle
import java.util.Random
import kotlin.math.pow


class SwipeStack @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context,attrs, defStyleAttr) {

    private var mAdapter: Adapter? = null
    private var mRandom: Random? = null

    var allowedSwipeDirections = 0
    private var mAnimationDuration = 0
    private var mCurrentViewIndex = 0
    private var mNumberOfStackedViews = 0
    private var mViewSpacing = 0
    private var mViewRotation = 0
    private var mSwipeRotation = 0f
    private var mSwipeOpacity = 0f
    private var mScaleFactor = 0f
    private var mDisableHwAcceleration = false
    private var mIsFirstLayout = true

    var topView: View? = null
        private set
    private var mSwipeHelper: SwipeHelper? = null
    private var mDataObserver: DataSetObserver? = null
    private var mListener: SwipeStackListener? = null
    private var mProgressListener: SwipeProgressListener? = null

    init {
        Log.e("MyLog", "SwipeStack -> init {}")
        readAttributes(attrs)
        initialize()
    }

    private fun readAttributes(attributeSet: AttributeSet?) {
        Log.e("MyLog", "SwipeStack -> readAttributes(attributeSet: AttributeSet?)")
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.SwipeStack)
        try {
            allowedSwipeDirections = attrs.getInt(
                R.styleable.SwipeStack_allowed_swipe_directions,
                SWIPE_DIRECTION_BOTH
            )
            mAnimationDuration = attrs.getInt(
                R.styleable.SwipeStack_animation_duration,
                DEFAULT_ANIMATION_DURATION
            )
            mNumberOfStackedViews =
                attrs.getInt(R.styleable.SwipeStack_stack_size, DEFAULT_STACK_SIZE)
            mViewSpacing = attrs.getDimensionPixelSize(
                R.styleable.SwipeStack_stack_spacing,
                resources.getDimensionPixelSize(R.dimen.default_stack_spacing)
            )
            mViewRotation = attrs.getInt(
                R.styleable.SwipeStack_stack_rotation, DEFAULT_STACK_ROTATION)
            mSwipeRotation = attrs.getFloat(
                R.styleable.SwipeStack_swipe_rotation, DEFAULT_SWIPE_ROTATION)
            mSwipeOpacity = attrs.getFloat(
                R.styleable.SwipeStack_swipe_opacity, DEFAULT_SWIPE_OPACITY)
            mScaleFactor = attrs.getFloat(
                R.styleable.SwipeStack_scale_factor, DEFAULT_SCALE_FACTOR)
            mDisableHwAcceleration = attrs.getBoolean(
                R.styleable.SwipeStack_disable_hw_acceleration, DEFAULT_DISABLE_HW_ACCELERATION )
        } finally {
            attrs.recycle()
        }
    }

    private fun initialize() {
        Log.e("MyLog", "SwipeStack -> initialize()")
        mRandom = Random()
        clipToPadding = false
        clipChildren = false

        mSwipeHelper = SwipeHelper(this)
        mSwipeHelper!!.setAnimationDuration(mAnimationDuration)
        mSwipeHelper!!.setRotation(mSwipeRotation)
        mSwipeHelper!!.setOpacityEnd(mSwipeOpacity)
        mDataObserver = object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                invalidate()
                requestLayout()
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState())
        bundle.putInt(KEY_CURRENT_INDEX, mCurrentViewIndex - childCount)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state: Parcelable? = state
        if (state is Bundle) {
            val bundle = state
            mCurrentViewIndex = bundle.getInt(KEY_CURRENT_INDEX)
            state = bundle.getParcelable(KEY_SUPER_STATE)
        }
        super.onRestoreInstanceState(state)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        if (mAdapter == null || mAdapter!!.isEmpty) {
            mCurrentViewIndex = 0
            removeAllViewsInLayout()
            return
        }
        var x = childCount
        while (x < mNumberOfStackedViews && mCurrentViewIndex < mAdapter!!.count) {
            addNextView()
            x++
        }
        reorderItems()
        mIsFirstLayout = false
    }

    private fun addNextView() {
        if (mCurrentViewIndex < mAdapter!!.count) {
            val bottomView = mAdapter!!.getView(mCurrentViewIndex, this, this)
            bottomView.setTag(R.id.new_view, true)
            if (!mDisableHwAcceleration) {
                bottomView.setLayerType(LAYER_TYPE_HARDWARE, null)
            }
            if (mViewRotation > 0) {
                bottomView.rotation =
                    (mRandom!!.nextInt(mViewRotation) - mViewRotation / 2).toFloat()
            }
            val width = width - (paddingLeft + paddingRight)
            val height = height - (paddingTop + paddingBottom)
            var params = bottomView.layoutParams
            if (params == null) {
                params = LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            }
            var measureSpecWidth = MeasureSpec.AT_MOST
            var measureSpecHeight = MeasureSpec.AT_MOST
            if (params.width == LayoutParams.MATCH_PARENT) {
                measureSpecWidth = MeasureSpec.EXACTLY
            }
            if (params.height == LayoutParams.MATCH_PARENT) {
                measureSpecHeight = MeasureSpec.EXACTLY
            }
            bottomView.measure(
                measureSpecWidth or width,
                measureSpecHeight or height)
            addViewInLayout(bottomView, 0, params, true)
            mCurrentViewIndex++

        }
    }

    private fun reorderItems() {
        for (x in 0 until childCount) {
            val childView = getChildAt(x)
            val topViewIndex = childCount - 1
            val distanceToViewAbove = topViewIndex * mViewSpacing - x * mViewSpacing
            val newPositionX = (width - childView.measuredWidth) / 2
            val newPositionY = distanceToViewAbove + paddingTop
            childView.layout(
                newPositionX,
                paddingTop,
                newPositionX + childView.measuredWidth,
                paddingTop + childView.minimumHeight
            )
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                childView.translationZ = x.toFloat()
            }
            val isNewView = childView.getTag(R.id.new_view) as Boolean
            val scaleFactor = mScaleFactor.toDouble().pow((childCount - x).toDouble()).toFloat()
            if (x == topViewIndex) {
                mSwipeHelper!!.unregisterObservedView()
                topView = childView
                mSwipeHelper!!.registerObservedView(
                    topView,
                    newPositionX.toFloat(),
                    newPositionY.toFloat()
                )
            }
            if (!mIsFirstLayout) {
                if (isNewView) {
                    childView.setTag(R.id.new_view, false)
                    childView.alpha = 0f
                    childView.y = newPositionY.toFloat()
                    childView.scaleY = scaleFactor
                    childView.scaleX = scaleFactor
                }
                childView.animate()
                    .y(newPositionY.toFloat())
                    .scaleX(scaleFactor)
                    .scaleY(scaleFactor)
                    .alpha(1f).duration = mAnimationDuration.toLong()
            } else {
                childView.setTag(R.id.new_view, false)
                childView.y = newPositionY.toFloat()
                childView.scaleY = scaleFactor
                childView.scaleX = scaleFactor
            }
        }
    }

    private fun removeTopView() {
        if (topView != null) {
            removeView(topView)
            topView = null
        }
        if (childCount == 0) {
            if (mListener != null) mListener!!.onStackEmpty()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    fun onSwipeStart() {
        if (mProgressListener != null) mProgressListener!!.onSwipeStart(currentPosition)
    }

    fun onSwipeProgress(progress: Float) {
        if (mProgressListener != null) mProgressListener!!.onSwipeProgress(currentPosition, progress)
    }

    fun onSwipeEnd() {
        if (mProgressListener != null) mProgressListener!!.onSwipeEnd(currentPosition)
    }

    fun onViewSwipeToLeft() {
        if (mListener != null) mListener!!.onViewSwipedToLeft(currentPosition)
        removeTopView()
    }

    fun onViewSwipeToRight() {
        if (mListener != null) mListener!!.onViewSwipedToRight(currentPosition)
        removeTopView()
    }

    val currentPosition: Int
        get() = mCurrentViewIndex - childCount
    var adapter: Adapter?
        get() = mAdapter
        set(adapter) {
            if (mAdapter != null) mAdapter!!.unregisterDataSetObserver(mDataObserver)
            mAdapter = adapter
            mAdapter!!.registerDataSetObserver(mDataObserver)
        }

    fun setListener(listener: SwipeStackListener?) {
        mListener = listener
    }

    fun setSwipeProgressListener(listener: SwipeProgressListener?) {
        mProgressListener = listener
    }

    fun swipeTopViewToRight() {
        if(childCount == 0) return
        mSwipeHelper!!.swipeViewToRight()
    }

    fun swipeTopViewToLeft() {
        if(childCount == 0) return
        mSwipeHelper!!.swipeViewToLeft()
    }

    fun resetStack() {
        mCurrentViewIndex = 0
        removeAllViewsInLayout()
        requestLayout()
    }

    interface SwipeStackListener {
        fun onViewSwipedToLeft(position: Int)
        fun onViewSwipedToRight(position: Int)
        fun onStackEmpty()
    }
    interface SwipeProgressListener {
        fun onSwipeStart(position: Int)
        fun onSwipeProgress(position: Int, progress: Float)
        fun onSwipeEnd(position: Int)
    }

    companion object {
        const val SWIPE_DIRECTION_BOTH = 0
        const val SWIPE_DIRECTION_ONLY_LEFT = 1
        const val SWIPE_DIRECTION_ONLY_RIGHT = 1
        const val DEFAULT_ANIMATION_DURATION = 300
        const val DEFAULT_STACK_SIZE = 3
        const val DEFAULT_STACK_ROTATION = 8
        const val DEFAULT_SWIPE_ROTATION = 30f
        const val DEFAULT_SWIPE_OPACITY = 1f
        const val DEFAULT_SCALE_FACTOR = 1f
        const val DEFAULT_DISABLE_HW_ACCELERATION = true
        private const val KEY_SUPER_STATE = "superState"
        private const val KEY_CURRENT_INDEX = "currentIndex"
    }

}