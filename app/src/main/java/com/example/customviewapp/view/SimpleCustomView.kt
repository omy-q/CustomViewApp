package com.example.customviewapp.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import com.example.customviewapp.R
import com.example.customviewapp.dp
import kotlin.math.min

class SimpleCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private val minViewWidth = 100.dp()
        private val minViewHeight = 100.dp()
    }

    private val backgroundPaint = Paint()
    private val rectPaint = Paint()
    private val circlePaint = Paint()
    private val roundRectPaint = Paint()

    private var widthSize: Int = 0
    private var heightSize: Int = 0

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        backgroundPaint.color = ContextCompat.getColor(context, R.color.white)
        rectPaint.color = ContextCompat.getColor(context, R.color.blue)
        circlePaint.color = ContextCompat.getColor(context, R.color.green)
        roundRectPaint.color = ContextCompat.getColor(context, R.color.yellow)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthSize = calculateSize(minViewWidth, widthMeasureSpec)
        heightSize = calculateSize(minViewHeight, heightMeasureSpec)
        setMeasuredDimension(
            widthSize + paddingStart + paddingEnd,
            heightSize + paddingTop + paddingBottom
        )
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawRectangle(canvas)
        drawCircle(canvas)
        drawRoundRect(canvas)
    }

    private fun drawRoundRect(canvas: Canvas) {
        canvas.drawRoundRect(
            (widthSize / 4 + paddingStart).toFloat(),
            (heightSize / 4 + paddingTop).toFloat(),
            (widthSize * 3 / 4 + paddingStart).toFloat(),
            (heightSize * 3 / 4 + paddingTop).toFloat(),
            30F,
            30F,
            roundRectPaint
        )
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(
            (widthSize / 2 + paddingStart).toFloat(),
            (heightSize / 2 + paddingTop).toFloat(),
            (widthSize / 2 - 10).toFloat(),
            circlePaint
        )
    }

    private fun drawRectangle(canvas: Canvas) {
        canvas.drawRect(
            paddingStart.toFloat(),
            paddingTop.toFloat(),
            (widthSize + paddingStart).toFloat(),
            (heightSize + paddingTop).toFloat(),
            rectPaint)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawRect(
            0F,
            0F,
            (widthSize + paddingStart + paddingEnd).toFloat(),
            (heightSize + paddingTop + paddingBottom).toFloat(),
            backgroundPaint
        )
    }
}