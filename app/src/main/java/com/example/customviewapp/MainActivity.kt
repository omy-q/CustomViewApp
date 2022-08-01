package com.example.customviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewapp.view.EmotionView
import com.example.customviewapp.view.WaterRippleView

class MainActivity : AppCompatActivity() {
    private lateinit var waterView: WaterRippleView
    private lateinit var emotionView: EmotionView
    private var clickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWaterRippleView()
        initEmotionView()
    }

    private fun initWaterRippleView() {
        waterView = findViewById(R.id.water_view)
        waterView.setOnClickListener {
            waterView.startAnimation()
        }
    }

    private fun initEmotionView() {
        emotionView = findViewById(R.id.emotion_view)
        emotionView.setOnClickListener {
            clickCount++
            if (clickCount > 2) {
                clickCount = 0
            }
            emotionView.emotionState = clickCount
        }
    }
}
