package com.drg.funtomim.MainPageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.drg.funtomim.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentMainPage : Fragment() {

    private lateinit var viewRoot: View
    private lateinit var fabStartGame: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewRoot = inflater.inflate(R.layout.fragment_main_page, container, false)
        fabStartGame = viewRoot.findViewById(R.id.fab_start_new_game)
        animateFab()
        setClickOnFab()
        return viewRoot
    }

    private fun animateFab() {
        fabStartGame.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_scale_no_limit))
    }

    private fun setClickOnFab() {

        fabStartGame.setOnClickListener {
            fragmentManager!!.beginTransaction()
                .setCustomAnimations(
                    R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out,
                    R.animator.anim_flip_right_in,
                    R.animator.anim_flip_right_out)
                .replace(R.id.fl_main, FragmentGameSettings())
                .addToBackStack("FRAGMENT_GAME_SETTINGS")
                .commit()
        }
    }

}
