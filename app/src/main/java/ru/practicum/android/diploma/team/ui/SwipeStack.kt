package ru.practicum.android.diploma.team.ui

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.FrameLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.util.thisName
import java.util.Random
import kotlin.math.pow

class SwipeStack @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var mAdapter: Adapter? = null
    private var mRandom: Random? = null
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

    /**
     * Get the view from the top of the stack.
     * @return The view if the stack is not empty or null otherwise.
     */
    var topView: View? = null
        private set
    private var mSwipeHelper: SwipeHelper? = null
    private var mDataObserver: DataSetObserver? = null
    private var mListener: SwipeStackListener? = null
    private var mProgressListener: SwipeProgressListener? = null

    init {
        readAttributes(attrs)
        initialize()
        Log.d("MyLog", "$thisName -> init {} mCurrentViewIndex=$mCurrentViewIndex")
    }

    private fun readAttributes(attributeSet: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.SwipeStack)
        try {
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
            mViewRotation =
                attrs.getInt(R.styleable.SwipeStack_stack_rotation, DEFAULT_STACK_ROTATION)
            mSwipeRotation =
                attrs.getFloat(R.styleable.SwipeStack_swipe_rotation, DEFAULT_SWIPE_ROTATION)
            mSwipeOpacity =
                attrs.getFloat(R.styleable.SwipeStack_swipe_opacity, DEFAULT_SWIPE_OPACITY)
            mScaleFactor = attrs.getFloat(R.styleable.SwipeStack_scale_factor, DEFAULT_SCALE_FACTOR)
            mDisableHwAcceleration = attrs.getBoolean(
                R.styleable.SwipeStack_disable_hw_acceleration,
                DEFAULT_DISABLE_HW_ACCELERATION
            )
        } finally {
            attrs.recycle()
        }
    }

    private fun initialize() {
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

    public override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState())
        bundle.putInt(KEY_CURRENT_INDEX, 0)
        return bundle // for new list
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        var currentState: Parcelable? = state
        if (currentState is Bundle) {
            val bundle = currentState
            mCurrentViewIndex = bundle.getInt(KEY_CURRENT_INDEX)
            @Suppress("DEPRECATION")
            currentState = bundle.getParcelable(KEY_SUPER_STATE)
        }
        super.onRestoreInstanceState(currentState)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
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
            val bottomView = mAdapter!!.getView(mCurrentViewIndex, null, this)
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
            bottomView.measure(measureSpecWidth or width, measureSpecHeight or height)
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
                paddingTop + childView.measuredHeight
            )
            childView.translationZ = x.toFloat()
            val isNewView = childView.getTag(R.id.new_view) as Boolean
            val scaleFactor =
                mScaleFactor.toDouble().pow((childCount - x).toDouble()).toFloat()
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
        if (mProgressListener != null) mProgressListener!!.onSwipeProgress(
            currentPosition,
            progress
        )
    }

    fun onSwipeEnd() {
        if (mProgressListener != null) mProgressListener!!.onSwipeEnd(currentPosition)
    }

    fun onViewSwipedToLeft() {
        if (mListener != null) mListener!!.onViewSwipedToLeft(currentPosition)
        removeTopView()
    }

    fun onViewSwipedToRight() {
        if (mListener != null) mListener!!.onViewSwipedToRight(currentPosition)
        removeTopView()
    }

    val currentPosition: Int
        /**
         * Returns the current adapter position.
         *
         * @return The current position.
         */
        get() = mCurrentViewIndex - childCount
    var adapter: Adapter?
        /**
         * Returns the adapter currently in use in this SwipeStack.
         *
         * @return The adapter currently used to display data in this SwipeStack.
         */
        get() = mAdapter
        /**
         * Sets the data behind this SwipeView.
         *
         * @param adapter The Adapter which is responsible for maintaining the
         * data backing this list and for producing a view to represent an
         * item in that data set.
         * @see .getAdapter
         */
        set(adapter) {
            if (mAdapter != null) mAdapter!!.unregisterDataSetObserver(mDataObserver)
            mAdapter = adapter
            mAdapter!!.registerDataSetObserver(mDataObserver)
        }

    /**
     * Register a callback to be invoked when the user has swiped the top view
     * left / right or when the stack gets empty.
     *
     * @param listener The callback that will run
     */
    fun setListener(listener: SwipeStackListener?) {
        mListener = listener
    }

    /**
     * Register a callback to be invoked when the user starts / stops interacting
     * with the top view of the stack.
     *
     * @param listener The callback that will run
     */
    fun setSwipeProgressListener(listener: SwipeProgressListener?) {
        mProgressListener = listener
    }

    /**
     * Programmatically dismiss the top view to the right.
     */
    fun swipeTopViewToRight() {
        if (childCount == 0) return
        mSwipeHelper!!.swipeViewToRight()
    }

    /**
     * Programmatically dismiss the top view to the left.
     */
    fun swipeTopViewToLeft() {
        if (childCount == 0) return
        mSwipeHelper!!.swipeViewToLeft()
    }

    /**
     * Resets the current adapter position and repopulates the stack.
     */
    fun resetStack() {
        mCurrentViewIndex = 0
        removeAllViewsInLayout()
        requestLayout()
    }

    /**
     * Interface definition for a callback to be invoked when the top view was
     * swiped to the left / right or when the stack gets empty.
     */
    interface SwipeStackListener {
        /**
         * Called when a view has been dismissed to the left.
         * @param position The position of the view in the adapter currently in use.
         */
        fun onViewSwipedToLeft(position: Int)

        /**
         * Called when a view has been dismissed to the right.
         * @param position The position of the view in the adapter currently in use.
         */
        fun onViewSwipedToRight(position: Int)

        /**
         * Called when the last view has been dismissed.
         */
        fun onStackEmpty()
    }

    /**
     * Interface definition for a callback to be invoked when the user
     * starts / stops interacting with the top view of the stack.
     */
    interface SwipeProgressListener {
        /**
         * Called when the user starts interacting with the top view of the stack.
         * @param position The position of the view in the currently set adapter.
         */
        fun onSwipeStart(position: Int)

        /**
         * Called when the user is dragging the top view of the stack.
         * @param position The position of the view in the currently set adapter.
         * @param progress Represents the horizontal dragging position in relation to
         * the start position of the drag.
         */
        fun onSwipeProgress(position: Int, progress: Float)

        /**
         * Called when the user has stopped interacting with the top view of the stack.
         * @param position The position of the view in the currently set adapter.
         */
        fun onSwipeEnd(position: Int)
    }

    companion object {
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