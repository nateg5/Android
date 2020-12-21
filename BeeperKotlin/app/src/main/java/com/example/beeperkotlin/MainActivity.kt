package com.example.beeperkotlin

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 90)

        timer("beep timer", false, 1000L, 1000L) {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP)
        }
    }
}