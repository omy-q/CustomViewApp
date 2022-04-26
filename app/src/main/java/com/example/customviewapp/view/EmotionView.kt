package com.example.customviewapp.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.customviewapp.R

class EmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLUE
        private const val DEFAULT_MOUTH_COLOR = Color.RED
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_VALUE = 4.0f

        const val NO_WINKS = 0
        const val WINKS_LEFT_EYE = 1
        const val WINKS_RIGHT_EYE = 2
    }

    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderSize = DEFAULT_BORDER_VALUE

    private val paint = Paint()
    private val eyesPath = Path()
    private val mouthPath = Path()

    var emotionState = NO_WINKS
        set(value) {
            field = value
            invalidate()
        }

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmotionView)
            faceColor = typedArray.getColor(R.styleable.EmotionView_faceColor, DEFAULT_FACE_COLOR)
            eyesColor = typedArray.getColor(R.styleable.EmotionView_eyesColor, DEFAULT_EYES_COLOR)
            mouthColor = typedArray.getColor(R.styleable.EmotionView_mouthColor, DEFAULT_MOUTH_COLOR)
            borderColor = typedArray.getColor(R.styleable.EmotionView_borderColor, DEFAULT_BORDER_COLOR)
            borderSize = typedArray.getDimension(R.styleable.EmotionView_borderSize, DEFAULT_BORDER_VALUE)
            emotionState = typedArray.getInt(R.styleable.EmotionView_emotionState, NO_WINKS)

            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFace(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFace(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = faceColor
        canvas.drawCircle(
            (width / 2 + paddingStart).toFloat(),
            (height / 2 + paddingTop).toFloat(),
            (width / 2).toFloat(),
            paint
        )

        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderSize
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            paint
        )
    }

    private fun drawEyes(canvas: Canvas) {
        eyesPath.reset()
        paint.reset()
        when (emotionState) {
            NO_WINKS -> {
                val leftEye = RectF(
                    (4 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (6 * width / 16).toFloat(),
                    (7 * height / 16).toFloat(),
                )
                val rightEye = RectF(
                    (10 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (12 * width / 16).toFloat(),
                    (7 * height / 16).toFloat(),
                )
                paint.style = Paint.Style.FILL
                paint.color = eyesColor
                canvas.drawOval(leftEye, paint)
                canvas.drawOval(rightEye, paint)

                paint.style = Paint.Style.STROKE
                paint.color = borderColor
                paint.strokeWidth = borderSize
                canvas.drawOval(leftEye, paint)
                canvas.drawOval(rightEye, paint)
            }
            WINKS_LEFT_EYE -> {
                eyesPath.moveTo((4 * width / 16).toFloat(), (6 * height / 16).toFloat())
                eyesPath.quadTo(
                    (5 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (6 * width / 16).toFloat(),
                    (6 * height / 16).toFloat(),
                )
                eyesPath.quadTo(
                    (5 * width / 16).toFloat(),
                    (5 * height / 16).toFloat(),
                    (4 * width / 16).toFloat(),
                    (6 * height / 16).toFloat(),
                )
                val rightEye = RectF(
                    (10 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (12 * width / 16).toFloat(),
                    (7 * height / 16).toFloat(),
                )
                paint.style = Paint.Style.FILL
                paint.color = eyesColor
                canvas.drawOval(rightEye, paint)
                paint.color = borderColor
                canvas.drawPath(eyesPath, paint)

                paint.style = Paint.Style.STROKE
                paint.color = borderColor
                paint.strokeWidth = borderSize
                canvas.drawOval(rightEye, paint)
            }
            WINKS_RIGHT_EYE -> {
                val leftEye = RectF(
                    (4 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (6 * width / 16).toFloat(),
                    (7 * height / 16).toFloat(),
                )
                eyesPath.moveTo((10 * width / 16).toFloat(), (6 * height / 16).toFloat())
                eyesPath.quadTo(
                    (11 * width / 16).toFloat(),
                    (4 * height / 16).toFloat(),
                    (12 * width / 16).toFloat(),
                    (6 * height / 16).toFloat(),
                )
                eyesPath.quadTo(
                    (11 * width / 16).toFloat(),
                    (5 * height / 16).toFloat(),
                    (10 * width / 16).toFloat(),
                    (6 * height / 16).toFloat(),
                )
                paint.style = Paint.Style.FILL
                paint.color = eyesColor
                canvas.drawOval(leftEye, paint)
                paint.color = borderColor
                canvas.drawPath(eyesPath, paint)

                paint.style = Paint.Style.STROKE
                paint.color = borderColor
                paint.strokeWidth = borderSize
                canvas.drawOval(leftEye, paint)
            }
        }
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.moveTo((3 * width / 16).toFloat(), (10 * height / 16).toFloat())
        mouthPath.lineTo((13 * width / 16).toFloat(), (10 * height / 16).toFloat())
        mouthPath.quadTo(
            (width / 2).toFloat(),
            (height + 2 * height / 16).toFloat(),
            (3 * width / 16).toFloat(),
            (10 * height / 16).toFloat(),
        )

        paint.style = Paint.Style.FILL
        paint.color = mouthColor
        canvas.drawPath(mouthPath, paint)

        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        paint.strokeWidth = borderSize
        canvas.drawPath(mouthPath, paint)
    }
}