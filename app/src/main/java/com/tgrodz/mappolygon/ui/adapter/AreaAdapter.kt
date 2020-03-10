package com.tgrodz.mappolygon.ui.adapter

import android.content.Context
import android.graphics.Color
import com.tgrodz.mappolygon.model.Area

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tgrodz.mappolygon.R
import kotlinx.android.synthetic.main.row_layout.view.*

class AreaAdapter (private var areas : ArrayList<Area>, private val listener : ItemListener )
    : RecyclerView.Adapter<AreaAdapter.ViewHolder>()   {

    private val colors : Array<String> = arrayOf("#8BC34A", "#FF7043")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return areas.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(areas[position], listener, colors, position)
    }

    fun setArticles(articles: ArrayList<Area>) {
        areas = articles
        notifyDataSetChanged()
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Area, listener: ItemListener, colors : Array<String>, position: Int) {

            itemView.setOnClickListener{ listener.onItemClick(item) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 2]))

            itemView.text_name.text = item.fieldNoDescr
            itemView.text_type.text = item.fieldNo
            itemView.text_code.text = item.cornType

        }
    }

}