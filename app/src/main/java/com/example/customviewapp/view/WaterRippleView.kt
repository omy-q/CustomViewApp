package com.example.customviewapp.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
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

    private fun initPoints(
        xStartValue: Float = -width.toFloat(),
        yDepthValue: Float = 0F
    ) {
        when (waterGravity) {
            TOP_WATER_GRAVITY -> initTopPoints(xStartValue, yDepthValue)
            BOTTOM_WATER_GRAVITY -> initBottomPoints(xStartValue, yDepthValue)
        }
    }

    private fun initTopPoints(xStartValue: Float, yDepthValue: Float) {
        val step = (width / 2).toFloat()
        val waveRipple = if (yDepthValue == height.toFloat()) 0F else (0.2 * height).toFloat()
        startZeroPoint = PointF(xStartValue, 0 + yDepthValue)
        secondZeroPoint = PointF(startZeroPoint.x + step, 0 + yDepthValue)
        thirdZeroPoint = PointF(secondZeroPoint.x + step, 0 + yDepthValue)
        fourthZeroPoint = PointF(thirdZeroPoint.x + step, 0 + yDepthValue)
        endZeroPoint = PointF(fourthZeroPoint.x + step, 0 + yDepthValue)

        controlFirstPoint =
            PointF(startZeroPoint.x + step / 2, 0 + yDepthValue - waveRipple)
        controlSecondPoint =
            PointF(secondZeroPoint.x + step / 2, 0 + yDepthValue + waveRipple)
        controlThirdPoint =
            PointF(thirdZeroPoint.x + step / 2, 0 + yDepthValue - waveRipple)
        controlFourthPoint =
            PointF(fourthZeroPoint.x + step / 2, 0 + yDepthValue + waveRipple)
    }

    private fun initBottomPoints(startX: Float, step: Float) {
        startZeroPoint = PointF(startX, (height).toFloat())
        secondZeroPoint = PointF(startZeroPoint.x + step, (height).toFloat())
        thirdZeroPoint = PointF(secondZeroPoint.x + step, (height).toFloat())
        fourthZeroPoint = PointF(thirdZeroPoint.x + step, (height).toFloat())
        endZeroPoint = PointF(fourthZeroPoint.x + step, (height).toFloat())

        controlFirstPoint = PointF(startZeroPoint.x + step / 2, (height - 0.2 * height).toFloat())
        controlSecondPoint = PointF(secondZeroPoint.x + step / 2, (height + 0.2 * height).toFloat())
        controlThirdPoint = PointF(thirdZeroPoint.x + step / 2, (height - 0.2 * height).toFloat())
        controlFourthPoint = PointF(fourthZeroPoint.x + step / 2, (height + 0.2 * height).toFloat())
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

    fun startAnimation() {
        val animator = ValueAnimator.ofFloat(0F, 1F)
        animator.duration = 4000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener {
            val animFactor = animator.animatedValue as Float
            val xStartValue = -width + animFactor * width
            val yDepthValue = animFactor * height
            initPoints(xStartValue, yDepthValue)
            invalidate()
        }
        if (!animationIsRunning) {
            animationIsRunning = true
            animator.start()
        } else {
            animationIsRunning = false
            animator.pause()
        }
    }
}