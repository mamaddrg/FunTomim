package com.drg.funtomim.GamePageFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.drg.funtomim.Adapters.GridViewCategoryAdapter

import com.drg.funtomim.R

class FragmentChooseCategory : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewRoot = inflater.inflate(R.layout.fragment_choose_category, container, false)
        val gridView = viewRoot.findViewById<GridView>(R.id.gv_categories)
        gridView.adapter = GridViewCategoryAdapter(context!!, arrayListOf("a","b","c","d","e","f","g"))
        return viewRoot
    }

}
