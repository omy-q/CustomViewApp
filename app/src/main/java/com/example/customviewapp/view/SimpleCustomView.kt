package com.example.customviewapp.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.view.*
import kotlin.math.min

class SimpleCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private val minViewWidth = (100 * Resources.getSystem().displayMetrics.density).toInt()
        private val minViewHeight = (100 * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = calculateSize(minViewWidth, widthMeasureSpec) + paddingStart + paddingEnd
        val height = calculateSize(minViewHeight, heightMeasureSpec) + paddingTop + paddingBottom
        setMeasuredDimension(width, height)
    }

    private fun calculateSize(size: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.AT_MOST -> {
                Log.d("size", "AT_MOST: ${min(size, specSize)}")
                min(size, specSize)
            }
            MeasureSpec.EXACTLY -> {
                Log.d("size", "EXACTLY: $specSize")
                specSize
            }
            else -> {
                Log.d("size", "UNSPECIFIED: $size")
                size
            }
        }
    }
}