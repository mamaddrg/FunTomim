package com.drg.funtomim

import android.animation.Animator
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnDecreaseTeams: Button
    private lateinit var btnIncreaseTeams: Button
    private lateinit var btnTimingAuto: Button
    private lateinit var btnTimingManual: Button
    private lateinit var btnStartGame : Button
    private lateinit var tvNumOfTeams: TextView
    private lateinit var tvNumOfRounds: TextView
    private lateinit var tvTimeOfRounds: TextView
    private lateinit var etFirstTeam: EditText
    private lateinit var etSecondTeam: EditText
    private lateinit var etThirdTeam: EditText
    private lateinit var etFourthTeam: EditText
    private lateinit var sbRoundsCount: IndicatorSeekBar
    private lateinit var sbManualTiming: IndicatorSeekBar

    private var isGameModeOpen = false
    private var numOfTeams = 2
    private var roundCount = 3
    private var isTimingAuto = true
    private var manualTimingSeconds = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        animateBackground()
        animateFab()
        setClickOnFab()
        setClickOnDecreaseTeams()
        setClickOnIncreaseTeams()
        setRoundCountListener()
        setManualTimingListener()
        setTimingModeButtonsClick()
        animateAutoTimingButtonForFirstTime()
        animateDisableTeamsForFirstTime()
        // Todo Change the logic behind this solution for both
        // when we want to set animation on something like this, animation will start from current situation
        // which cause some problems, because we set alpha to 0.5 and scale to 0.8 from xml by default

    }


    override fun onBackPressed() {

        if (isGameModeOpen)
            closeGameMode()
        else
            super.onBackPressed()
    }

    private fun initViews() {

        btnDecreaseTeams = findViewById(R.id.btn_decrease_players)
        btnIncreaseTeams = findViewById(R.id.btn_increase_players)
        btnTimingAuto = findViewById(R.id.btn_auto_timing_for_round)
        btnTimingManual = findViewById(R.id.btn_manual_timing_for_round)
        btnStartGame = findViewById(R.id.btn_start_game)
        tvNumOfTeams = findViewById(R.id.tv_show_num_of_teams)
        tvNumOfRounds = findViewById(R.id.tv_show_num_of_rounds)
        tvTimeOfRounds = findViewById(R.id.tv_show_manual_time)
        etFirstTeam = findViewById(R.id.et_first_team_name)
        etSecondTeam = findViewById(R.id.et_second_team_name)
        etThirdTeam = findViewById(R.id.et_third_team_name)
        etFourthTeam = findViewById(R.id.et_fourth_team_name)
        sbRoundsCount = findViewById(R.id.seek_num_of_rounds)
        sbManualTiming = findViewById(R.id.seek_manual_timing)
    }

    private fun animateBackground() {

        val animation = main_layout.background as AnimationDrawable
        animation.apply {
            setEnterFadeDuration(2000)
            setExitFadeDuration(4000)
            start()
        }
    }

    private fun animateFab() {

        val scaleAnim = AnimationUtils.loadAnimation(this, R.anim.anim_scale_no_limit)
        fab_start_new_game.startAnimation(scaleAnim)
    }

    private fun setClickOnFab() {

        fab_start_new_game.setOnClickListener {
            if (isGameModeOpen) closeGameMode() else openGameMode()
        }
    }

    private fun closeGameMode() {

        val x = main_layout.left
        val y = main_layout.top
        val startRadius = Math.hypot(main_layout.width.toDouble() , main_layout.height.toDouble())

        val animator = ViewAnimationUtils.createCircularReveal(layout_mode, x, y, startRadius.toFloat(), 0f)
        animator.apply {
            start()
            addListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    layout_mode.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
        hideOrShowFab()
        isGameModeOpen = !isGameModeOpen
    }

    private fun openGameMode() {

        val x = main_layout.right
        val y = main_layout.bottom
        val endRadius = Math.hypot(main_layout.width.toDouble() , main_layout.height.toDouble())

        val animator = ViewAnimationUtils.createCircularReveal(layout_mode, x, y, 0f, endRadius.toFloat())
        layout_mode.visibility = View.VISIBLE
        animator.start()
        hideOrShowFab()
        isGameModeOpen = !isGameModeOpen
    }

    private fun hideOrShowFab() {

        if (isGameModeOpen) {
            fab_start_new_game.animate().apply {
                alpha(1f)
                duration = 800
            }
        } else {
            fab_start_new_game.animate().apply {
                alpha(0f)
                duration = 800
            }
        }
    }

    private fun setClickOnDecreaseTeams() {

        btnDecreaseTeams.setOnClickListener {

            if (numOfTeams > 2) {
                numOfTeams--
                tvNumOfTeams.text = numOfTeams.toString()
                disableTeam(numOfTeams)
            }
        }
    }

    private fun setClickOnIncreaseTeams() {

        btnIncreaseTeams.setOnClickListener {

            if (numOfTeams < 4) {
                numOfTeams++
                tvNumOfTeams.text = numOfTeams.toString()
                enableTeam(numOfTeams)
            }
        }
    }

    private fun enableTeam(teamNumber: Int) {

        when (teamNumber) {

            3 -> {
                etThirdTeam.isEnabled = true
                etThirdTeam.clearAnimation()
                etThirdTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_select_edittext) }

            4 -> {
                etFourthTeam.isEnabled = true
                etFourthTeam.clearAnimation()
                etFourthTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_select_edittext) }
        }
    }

    private fun disableTeam(teamNumber: Int) {

        when (teamNumber) {

            2 -> {
                etThirdTeam.isEnabled = false
                etThirdTeam.clearAnimation()
                etThirdTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_deselect_edittext) }

            3 -> {
                etFourthTeam.isEnabled = false
                etFourthTeam.clearAnimation()
                etFourthTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_deselect_edittext) }
        }
    }

    private fun setRoundCountListener() {

        sbRoundsCount.onSeekChangeListener = object: OnSeekChangeListener {

            override fun onSeeking(seekParams: SeekParams?) {
                roundCount = seekParams!!.progress
                tvNumOfRounds.text = "${seekParams.progress} دور"
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {}
        }
    }

    private fun setManualTimingListener() {

        sbManualTiming.onSeekChangeListener = object: OnSeekChangeListener {

            override fun onSeeking(seekParams: SeekParams?) {
                manualTimingSeconds = seekParams!!.progress
                updateTimingTextView()
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {}
        }
    }

    private fun setTimingModeButtonsClick() {

        btnTimingAuto.setOnClickListener {

            if (!isTimingAuto) {

                isTimingAuto = !isTimingAuto
                hideManualTimingSeekBar()
                updateTimingTextView()

                btnTimingAuto.apply {
                    background = resources.getDrawable(R.drawable.selected_button_purple, null)
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_select_button)
                }

                btnTimingManual.apply {
                    background = resources.getDrawable(R.drawable.round_corners_grey_background , null)
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_deselect_button)
                }
            }
        }

        btnTimingManual.setOnClickListener {

            if (isTimingAuto) {

                isTimingAuto = !isTimingAuto
                showManualTimingSeekBar()
                updateTimingTextView()

                btnTimingAuto.apply {
                    background = resources.getDrawable(R.drawable.round_corners_grey_background, null)
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_deselect_button)
                }

                btnTimingManual.apply {
                    background = resources.getDrawable(R.drawable.selected_button_purple , null)
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_select_button)
                }
            }
        }
    }

    private fun showManualTimingSeekBar() {

        sbManualTiming.clearAnimation()
        sbManualTiming.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_show_manual_timing_seekbar)
        sbManualTiming.animation = anim
    }

    private fun hideManualTimingSeekBar() {

        sbManualTiming.clearAnimation()
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_hide_manual_timing_seekbar)
        sbManualTiming.animation = anim.apply {

            setAnimationListener(object: Animation.AnimationListener{

                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    sbManualTiming.visibility = View.GONE
                }
            })
        }

    }

    private fun updateTimingTextView() {

        if (isTimingAuto) {
            tvTimeOfRounds.text = "وابسته به میزان سختی"
        } else {
            tvTimeOfRounds.text = "$manualTimingSeconds ثانیه"
        }
    }

    private fun animateAutoTimingButtonForFirstTime() {

        btnTimingAuto.apply {
            background = resources.getDrawable(R.drawable.selected_button_purple, null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_select_button)
        }
    }

    private fun animateDisableTeamsForFirstTime() {

        etThirdTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_deselect_edittext)
        etFourthTeam.animation = AnimationUtils.loadAnimation(this, R.anim.anim_deselect_edittext)
    }

}