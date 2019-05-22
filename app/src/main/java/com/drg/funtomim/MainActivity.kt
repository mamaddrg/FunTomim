package com.drg.funtomim

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation = main_layout.background as AnimationDrawable
        animation.apply {
            setEnterFadeDuration(2000)
            setExitFadeDuration(4000)
            start()
        }

    }
}
