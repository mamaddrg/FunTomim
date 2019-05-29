package com.drg.funtomim.GamePageFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.drg.funtomim.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.graphics.drawable.TransitionDrawable
import android.view.animation.AnimationUtils

class BottomSheetScore: BottomSheetDialogFragment() {

    private var selectedButton: Button? = null
    private var isNothingSelected = true
    private lateinit var btnStart: Button

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val viewRoot = inflater.inflate(R.layout.bottom_sheet_score, container, false)
        val btnEasy = viewRoot.findViewById<Button>(R.id.btn_easy_sheet)
        val btnRegular = viewRoot.findViewById<Button>(R.id.btn_regular_sheet)
        val btnHard = viewRoot.findViewById<Button>(R.id.btn_hard_sheet)
        btnStart = viewRoot.findViewById(R.id.btn_start_sheet)

        btnEasy.setOnClickListener {

            if (isNothingSelected)
                showStartButton()

            animateDeSelectedButton(selectedButton)
            animateSelectedButton(it as Button)
            selectedButton = it
        }

        btnRegular.setOnClickListener {

            if (isNothingSelected)
                showStartButton()

            animateDeSelectedButton(selectedButton)
            animateSelectedButton(it as Button)
            selectedButton = it
        }

        btnHard.setOnClickListener {

            if (isNothingSelected)
                showStartButton()

            animateDeSelectedButton(selectedButton)
            animateSelectedButton(it as Button)
            selectedButton = it
        }

        btnStart.setOnClickListener {

            fragmentManager!!.beginTransaction()
                .setCustomAnimations(R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out,
                    R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out)
                .replace(R.id.fl_main, FragmentGame())
                .addToBackStack("FRAGMENT_GAME")
                .commit()
        }

        return viewRoot
    }

    private fun animateSelectedButton(button: Button) {

        val transition = button.background as TransitionDrawable
        transition.startTransition(300)

        button.animate().apply {
            scaleX(1.1f)
            scaleY(1.1f)
            duration = 300
        }
    }

    private fun animateDeSelectedButton(button: Button?) {

        button?.apply {

            val transition = background as TransitionDrawable
            transition.reverseTransition(300)

            animate()?.apply {
                scaleX(1f)
                scaleY(1f)
                duration = 300
            }
        }

    }

    private fun showStartButton() {

        btnStart.visibility = View.VISIBLE
        btnStart.animation = AnimationUtils
            .loadAnimation(context, R.anim.anim_translate_from_bottom_left).apply {
            start()
        }
    }

}