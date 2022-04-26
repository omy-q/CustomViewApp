package com.example.customviewapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.customviewapp.R
import com.example.customviewapp.dp

class EmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val faceColor = R.color.rich_yellow
        private const val eyesColor = R.color.dark_blue
        private const val mouthColor = R.color.red
        private const val borderColor = R.color.black

        private val borderSize = 2.dp()
    }

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFace(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFace(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, faceColor)
        canvas.drawCircle(
            (width / 2 + paddingStart).toFloat(),
            (height / 2 + paddingTop).toFloat(),
            (width / 2).toFloat(),
            paint
        )

        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, borderColor)
        paint.strokeWidth = borderSize.toFloat()
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            paint
        )
    }

    private fun drawEyes(canvas: Canvas) {
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
        paint.color = ContextCompat.getColor(context, eyesColor)
        canvas.drawOval(leftEye, paint)
        canvas.drawOval(rightEye, paint)

        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, borderColor)
        paint.strokeWidth = borderSize.toFloat()
        canvas.drawOval(leftEye, paint)
        canvas.drawOval(rightEye, paint)
    }

    private fun drawMouth(canvas: Canvas) {
        path.moveTo((3 * width / 16).toFloat(), (10 * height / 16).toFloat())
        path.lineTo((13 * width / 16).toFloat(), (10 * height / 16).toFloat())
        path.quadTo(
            (width / 2).toFloat(),
            (height + 2 * height / 16).toFloat(),
            (3 * width / 16).toFloat(),
            (10 * height / 16).toFloat(),
        )

        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, mouthColor)
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.STROKE
        paint.color = ContextCompat.getColor(context, borderColor)
        paint.strokeWidth = borderSize.toFloat()
        canvas.drawPath(path, paint)
    }
}