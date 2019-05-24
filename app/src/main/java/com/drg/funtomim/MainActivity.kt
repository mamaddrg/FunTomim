package com.drg.funtomim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drg.funtomim.MainPageFragments.FragmentMainPage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        putFragments()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
        // we are using fragments to show anything inside mainActivity
        // and fragment manager has a back tracer for back button
        // so when back is pressed, if backStackEntryCount == 0 then
        // we have no fragments and app should be finished
    }

    private fun putFragments() {
        // when activity starts, we put FragmentMainPage in it
        // which includes basic things about game
        // everything starts in this fragment

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_main, FragmentMainPage())
            .addToBackStack("FRAGMENT_MAIN_PAGE")
            .commit()
    }

    /*
    private fun addLayoutChangeListener() {

        fl_main.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val x = fl_main.left
            val y = fl_main.bottom
            val startRadius = Math.hypot(fl_main.width.toDouble(), fl_main.height.toDouble())
            ViewAnimationUtils.createCircularReveal(fl_main, x, y, 0f, startRadius.toFloat()).apply {
                duration = 1200
                start() }
        }
    }

    private fun animateBackground() {

        val animation = main_layout.background as AnimationDrawable
        animation.apply {
            setEnterFadeDuration(2000)
            setExitFadeDuration(4000)
            start()
        }
    }
    */
}