package com.drg.funtomim.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.drg.funtomim.R

class GridViewCategoryAdapter(private val context: Context,
                              private val listCategories: ArrayList<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        return if (convertView == null)
            LayoutInflater.from(context).inflate(R.layout.item_category , parent, false)
        else
            convertView
    }

    override fun getItem(position: Int): Any {
        return listCategories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listCategories.size
    }


}