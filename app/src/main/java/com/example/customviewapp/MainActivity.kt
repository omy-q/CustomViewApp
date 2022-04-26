package com.example.customviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewapp.view.EmotionView

class MainActivity : AppCompatActivity() {
    private var emotionView: EmotionView? = null
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        emotionView = findViewById(R.id.emotion_view)
        emotionView?.setOnClickListener {
            clickCount ++
            if (clickCount > 2) {
                clickCount = 0
            }
            emotionView?.emotionState = clickCount
        }
    }
}