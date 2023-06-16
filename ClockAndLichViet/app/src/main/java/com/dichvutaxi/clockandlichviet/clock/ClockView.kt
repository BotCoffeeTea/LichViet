package com.dichvutaxi.clockandlichviet.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.*

class ClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val hourHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val minuteHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val secondHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dialPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private var hourHandAngle = 0f
    private var minuteHandAngle = 0f
    private var secondHandAngle = 0f
    private var hourHandLength = 0.0
    private var minuteHandLength = 0.0
    private var secondHandLength = 0.0

    init {
        hourHandPaint.color = Color.BLACK
        hourHandPaint.style = Paint.Style.STROKE
        hourHandPaint.strokeWidth = 10f

        minuteHandPaint.color = Color.GRAY
        minuteHandPaint.style = Paint.Style.STROKE
        minuteHandPaint.strokeWidth = 5f

        secondHandPaint.color = Color.RED
        secondHandPaint.style = Paint.Style.STROKE
        secondHandPaint.strokeWidth = 2f

        dialPaint.color = Color.BLACK
        dialPaint.style = Paint.Style.STROKE
        dialPaint.strokeWidth = 5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (Math.min(w, h) / 2).toFloat()
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDial(canvas)
        drawHourHand(canvas)
        drawMinuteHand(canvas)
        drawSecondHand(canvas)
        invalidate()
    }

    private fun drawDial(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius, dialPaint)
    }

    private fun drawHourHand(canvas: Canvas) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hourAngle = (360 / 12) * hour
        hourHandAngle = hourAngle.toFloat()
        val hourHandPath = Path()
        hourHandPath.moveTo(centerX, centerY)
        val hourHandX = centerX + hourHandLength * Math.sin(Math.toRadians(hourAngle.toDouble()))
        val hourHandY = centerY - hourHandLength * Math.cos(Math.toRadians(hourAngle.toDouble()))
        hourHandPath.lineTo(hourHandX.toFloat(), hourHandY.toFloat())
        canvas.drawPath(hourHandPath, hourHandPaint)
    }

    private fun drawMinuteHand(canvas: Canvas) {
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        val minuteAngle = (360 / 60) * minute
        minuteHandAngle = minuteAngle.toFloat()
        val minuteHandPath = Path()
        minuteHandPath.moveTo(centerX, centerY)
        val minuteHandX = centerX + minuteHandLength * Math.sin(Math.toRadians(minuteAngle.toDouble()))
        val minuteHandY = centerY - minuteHandLength * Math.cos(Math.toRadians(minuteAngle.toDouble()))
        minuteHandPath.lineTo(minuteHandX.toFloat(), minuteHandY.toFloat())
        canvas.drawPath(minuteHandPath, minuteHandPaint)
    }

    private fun drawSecondHand(canvas: Canvas) {
        val second = Calendar.getInstance().get(Calendar.SECOND)
        val secondAngle = (360 / 60) * second
        secondHandTiếp tục mã nguồn:

        Angle = secondAngle.toFloat()
        val secondHandPath = Path()
        secondHandPath.moveTo(centerX, centerY)
        val secondHandX = centerX + secondHandLength * Math.sin(Math.toRadians(secondAngle.toDouble()))
        val secondHandY = centerY - secondHandLength * Math.cos(Math.toRadians(secondAngle.toDouble()))
        secondHandPath.lineTo(secondHandX.toFloat(), secondHandY.toFloat())
        canvas.drawPath(secondHandPath, secondHandPaint)
    }
}
