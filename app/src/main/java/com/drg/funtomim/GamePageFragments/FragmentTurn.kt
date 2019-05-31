package com.drg.funtomim.GamePageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

import com.drg.funtomim.R

class FragmentTurn : Fragment() {

    private lateinit var btnStart: Button
    private lateinit var tvRoundCounter: TextView
    private lateinit var tvFirstTeameName: TextView
    private lateinit var tvSecondTeameName: TextView
    private lateinit var tvThirdTeameName: TextView
    private lateinit var tvFourthTeameName: TextView
    private lateinit var tvFirstTeameScores: TextView
    private lateinit var tvSecondTeameScores: TextView
    private lateinit var tvThirdTeameScores: TextView
    private lateinit var tvFourthTeameScores: TextView
    private lateinit var cbFirstTeameTurn: CheckBox
    private lateinit var cbSecondTeameTurn: CheckBox
    private lateinit var cbThirdTeameTurn: CheckBox
    private lateinit var cbForthTeameTurn: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewRoot = inflater.inflate(R.layout.fragment_turn, container, false)
        initViews(viewRoot)
        animateStartButton()
        setClickOnStartGame()
        return viewRoot
    }


    private fun initViews(root: View) {

        root.apply {
            btnStart = findViewById(R.id.btn_start_game_turn)
            tvRoundCounter = findViewById(R.id.tv_round_counter)
            tvFirstTeameName = findViewById(R.id.tv_first_team_name)
            tvSecondTeameName = findViewById(R.id.tv_second_team_name)
            tvThirdTeameName = findViewById(R.id.tv_third_team_name)
            tvFourthTeameName = findViewById(R.id.tv_fourth_team_name)
            tvFirstTeameScores = findViewById(R.id.tv_first_team_scores)
            tvSecondTeameScores = findViewById(R.id.tv_second_team_scores)
            tvThirdTeameScores = findViewById(R.id.tv_third_team_scores)
            tvFourthTeameScores = findViewById(R.id.tv_fourth_team_scores)
            cbFirstTeameTurn = findViewById(R.id.cb_first_team_turn)
            cbSecondTeameTurn = findViewById(R.id.cb_second_team_turn)
            cbThirdTeameTurn = findViewById(R.id.cb_third_team_turn)
            cbForthTeameTurn = findViewById(R.id.cb_fourth_team_turn)
        }
    }


    private fun animateStartButton() {

        btnStart.animation = AnimationUtils
            .loadAnimation(context, R.anim.anim_zoom_in_out_no_limit)
    }


    private fun setClickOnStartGame() {

        btnStart.setOnClickListener {

            fragmentManager!!.beginTransaction()
                .setCustomAnimations(R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out,
                    R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out)
                .replace(R.id.fl_main, FragmentChooseCategory())
                .addToBackStack("FRAGMENT_GAME")
                .commit()
        }
    }

}
