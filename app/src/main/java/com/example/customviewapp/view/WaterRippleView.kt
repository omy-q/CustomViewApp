package com.example.customviewapp.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator

class WaterRippleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val TOP_WATER_GRAVITY = 0
        private const val BOTTOM_WATER_GRAVITY = 1
        private const val DEFAULT_WATER_COLOR = Color.BLUE

        private const val INIT_X = 0F
        private const val INIT_Y = 0F
    }

    private val waterPath = Path()
    private val waterPaint = Paint()
    private var animationIsRunning: Boolean = false

    private var startZeroPoint = PointF(INIT_X, INIT_Y)
    private var secondZeroPoint = PointF(INIT_X, INIT_Y)
    private var thirdZeroPoint = PointF(INIT_X, INIT_Y)
    private var fourthZeroPoint = PointF(INIT_X, INIT_Y)
    private var endZeroPoint = PointF(INIT_X, INIT_Y)

    private var controlFirstPoint = PointF(INIT_X, INIT_Y)
    private var controlSecondPoint = PointF(INIT_X, INIT_Y)
    private var controlThirdPoint = PointF(INIT_X, INIT_Y)
    private var controlFourthPoint = PointF(INIT_X, INIT_Y)

    var waterGravity = TOP_WATER_GRAVITY
        set(value) {
            field = value
            requestLayout()
        }
    var waterColor = DEFAULT_WATER_COLOR
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    init {
        waterPaint.color = waterColor
        waterPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPoints()
    }

    private fun initPoints() {
        when (waterGravity) {
            TOP_WATER_GRAVITY -> initTopPoints()
            BOTTOM_WATER_GRAVITY -> initBottomPoints()
        }
    }

    private fun initTopPoints() {
        startZeroPoint = PointF((-width).toFloat(), (0).toFloat())
        secondZeroPoint = PointF((-width / 2).toFloat(), (0).toFloat())
        thirdZeroPoint = PointF(0F, (0).toFloat())
        fourthZeroPoint = PointF((width / 2).toFloat(), (0).toFloat())
        endZeroPoint = PointF(width.toFloat(), (0).toFloat())

        controlFirstPoint = PointF((-3 * width / 4).toFloat(), (-0.2 * height).toFloat())
        controlSecondPoint = PointF((-width / 4).toFloat(), (0.2 * height).toFloat())
        controlThirdPoint = PointF((width / 4).toFloat(), (-0.2 * height).toFloat())
        controlFourthPoint = PointF((3 * width / 4).toFloat(), (0.2 * height).toFloat())
    }

    private fun initBottomPoints() {
        startZeroPoint = PointF((-width).toFloat(), (height).toFloat())
        secondZeroPoint = PointF((-width / 2).toFloat(), (height).toFloat())
        thirdZeroPoint = PointF(0F, (height).toFloat())
        fourthZeroPoint = PointF((width / 2).toFloat(), (height).toFloat())
        endZeroPoint = PointF(width.toFloat(), (height).toFloat())

        controlFirstPoint = PointF((-3 * width / 4).toFloat(), (height - 0.2 * height).toFloat())
        controlSecondPoint = PointF((-width / 4).toFloat(), (height + 0.2 * height).toFloat())
        controlThirdPoint = PointF((width / 4).toFloat(), (height - 0.2 * height).toFloat())
        controlFourthPoint = PointF((3 * width / 4).toFloat(), (height + 0.2 * height).toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (waterGravity) {
            TOP_WATER_GRAVITY -> drawTopWater(canvas)
            BOTTOM_WATER_GRAVITY -> drawBottomWater(canvas)
        }
    }

    private fun drawTopWater(canvas: Canvas) {
        drawWater()
        waterPath.lineTo(endZeroPoint.x, (0).toFloat())
        waterPath.lineTo((-width).toFloat(), (0).toFloat())
        waterPath.lineTo((-width).toFloat(), startZeroPoint.y)
        canvas.drawPath(waterPath, waterPaint)
        waterPath.reset()
    }

    private fun drawBottomWater(canvas: Canvas) {
        drawWater()
        waterPath.lineTo(endZeroPoint.x, (height).toFloat())
        waterPath.lineTo((-width).toFloat(), (height).toFloat())
        waterPath.lineTo((-width).toFloat(), startZeroPoint.y)
        canvas.drawPath(waterPath, waterPaint)
        waterPath.reset()
    }

    private fun drawWater() {
        waterPath.moveTo(startZeroPoint.x, startZeroPoint.y)
        waterPath.quadTo(
            controlFirstPoint.x,
            controlFirstPoint.y,
            secondZeroPoint.x,
            secondZeroPoint.y
        )
        waterPath.quadTo(
            controlSecondPoint.x,
            controlSecondPoint.y,
            thirdZeroPoint.x,
            thirdZeroPoint.y
        )
        waterPath.quadTo(
            controlThirdPoint.x,
            controlThirdPoint.y,
            fourthZeroPoint.x,
            fourthZeroPoint.y
        )
        waterPath.quadTo(
            controlFourthPoint.x,
            controlFourthPoint.y,
            endZeroPoint.x,
            endZeroPoint.y
        )
    }

}