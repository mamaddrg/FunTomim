package com.drg.funtomim.MainPageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.drg.funtomim.GamePageFragments.FragmentChooseCategory
import com.drg.funtomim.R
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams

class FragmentGameSettings : Fragment() {

    private lateinit var viewRoot: View
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

    private var numOfTeams = 2
    private var roundCount = 3
    private var isTimingAuto = true
    private var manualTimingSeconds = 90

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewRoot = inflater.inflate(R.layout.fragment_game_settings, container, false)
        initViews()
        setClickOnDecreaseTeams()
        setClickOnIncreaseTeams()
        setRoundCountListener()
        setManualTimingListener()
        setTimingModeButtonsClick()
        // when this page will be shown for the first time, there are some default chooses
        // like auto timing button, 2 active teams. we want some changes on selected items
        // like alpha, scale, background and etc. we have to set them manually for first time
        // TODO change the logic behind first time items styling (2 functions bellow)
        animateAutoTimingButtonForFirstTime()
        animateDisableTeamsForFirstTime()
        setClickOnStartGame()
        return viewRoot
    }

    private fun initViews() {

        btnDecreaseTeams = viewRoot.findViewById(R.id.btn_decrease_players)
        btnIncreaseTeams = viewRoot.findViewById(R.id.btn_increase_players)
        btnTimingAuto = viewRoot.findViewById(R.id.btn_auto_timing_for_round)
        btnTimingManual = viewRoot.findViewById(R.id.btn_manual_timing_for_round)
        btnStartGame = viewRoot.findViewById(R.id.btn_start_game)
        tvNumOfTeams = viewRoot.findViewById(R.id.tv_show_num_of_teams)
        tvNumOfRounds = viewRoot.findViewById(R.id.tv_show_num_of_rounds)
        tvTimeOfRounds = viewRoot.findViewById(R.id.tv_show_manual_time)
        etFirstTeam = viewRoot.findViewById(R.id.et_first_team_name)
        etSecondTeam = viewRoot.findViewById(R.id.et_second_team_name)
        etThirdTeam = viewRoot.findViewById(R.id.et_third_team_name)
        etFourthTeam = viewRoot.findViewById(R.id.et_fourth_team_name)
        sbRoundsCount = viewRoot.findViewById(R.id.seek_num_of_rounds)
        sbManualTiming = viewRoot.findViewById(R.id.seek_manual_timing)

    }

    private fun setClickOnDecreaseTeams() {
        // Number of teams must be 2 at least
        // and 4 at maximum for now
        // so we check for teams to always be between 2 and 4

        btnDecreaseTeams.setOnClickListener {

            if (numOfTeams > 2) {
                numOfTeams--
                tvNumOfTeams.text = numOfTeams.toString()
                disableTeam(numOfTeams)
            }
        }
    }

    private fun setClickOnIncreaseTeams() {
        // Number of teams must be 2 at least
        // and 4 at maximum for now
        // so we check for teams to always be between 2 and 4

        btnIncreaseTeams.setOnClickListener {

            if (numOfTeams < 4) {
                numOfTeams++
                tvNumOfTeams.text = numOfTeams.toString()
                enableTeam(numOfTeams)
            }
        }
    }

    private fun enableTeam(teamNumber: Int) {
        // this function called every time that user increase teams
        // when a teams number increased, it means we should enable it's editText
        // and also animate that, we do that here.
        // if teams count is x now, so we should enable x

        when (teamNumber) {

            3 -> {
                etThirdTeam.apply {
                    isEnabled = true
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(context, R.anim.anim_select_edittext) }
            }

            4 -> {
                etFourthTeam.apply {
                    isEnabled = true
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(context, R.anim.anim_select_edittext) }
            }
        }
    }

    private fun disableTeam(teamNumber: Int) {
        // this function called every time that user decrease teams
        // when a teams number decreased, it means we should disable it's editText
        // and also animate that, we do that here.
        // if teams count is x now, so we should disable x+1

        when (teamNumber) {

            2 -> {
                etThirdTeam.apply {
                    isEnabled = false
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_edittext) }
            }

            3 -> {
                etFourthTeam.apply {
                    isEnabled = false
                    clearAnimation()
                    animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_edittext) }
            }
        }
    }

    private fun setRoundCountListener() {
        // if user change rounds count using seek bar
        // we handle the changes here

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
        // if user change time of each round using seek bar
        // we handle the changes here

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
        // when user switches between auto timing and manual timing,
        // we want to show animations on items which are going to hide and visible,
        // also we will show manual timing seek bar only when manual timing is enable.

        btnTimingAuto.setOnClickListener {

            if (!isTimingAuto) {
                isTimingAuto = !isTimingAuto
                hideManualTimingSeekBar()
                updateTimingTextView()
                showAnimationsForManualTimingMode()
            }
        }

        btnTimingManual.setOnClickListener {

            if (isTimingAuto) {
                isTimingAuto = !isTimingAuto
                showManualTimingSeekBar()
                updateTimingTextView()
                showAnimationsForAutoTimingMode()
            }
        }
    }

    private fun showAnimationsForManualTimingMode() {

        btnTimingAuto.apply {
            background = resources.getDrawable(R.drawable.selected_button_purple, null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_select_button)
        }

        btnTimingManual.apply {
            background = resources.getDrawable(R.drawable.round_corners_grey_background , null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_button)
        }
    }

    private fun showAnimationsForAutoTimingMode() {

        btnTimingAuto.apply {
            background = resources.getDrawable(R.drawable.round_corners_grey_background, null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_button)
        }

        btnTimingManual.apply {
            background = resources.getDrawable(R.drawable.selected_button_purple , null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_select_button)
        }
    }

    private fun showManualTimingSeekBar() {
        // animations on manual timing seek bar when it's going to be visible

        sbManualTiming.clearAnimation()
        sbManualTiming.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_show_manual_timing_seekbar)
        sbManualTiming.animation = anim
    }

    private fun hideManualTimingSeekBar() {
        // animations on manual timing seek bar when it's going to be hide

        sbManualTiming.clearAnimation()
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_hide_manual_timing_seekbar)
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
        // update timing text when user switches between auto and manual timing

        if (isTimingAuto)
            tvTimeOfRounds.text = "وابسته به میزان سختی"
        else
            tvTimeOfRounds.text = "$manualTimingSeconds ثانیه"
    }

    private fun animateAutoTimingButtonForFirstTime() {

        btnTimingAuto.apply {
            background = resources.getDrawable(R.drawable.selected_button_purple, null)
            clearAnimation()
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_select_button)
        }
    }

    private fun animateDisableTeamsForFirstTime() {

        etThirdTeam.animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_edittext)
        etFourthTeam.animation = AnimationUtils.loadAnimation(context, R.anim.anim_deselect_edittext)
    }

    private fun setClickOnStartGame() {

        btnStartGame.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .add(R.id.fl_main, FragmentChooseCategory())
                .addToBackStack("fnwgkbw")
                .commit()
        }
    }

}
