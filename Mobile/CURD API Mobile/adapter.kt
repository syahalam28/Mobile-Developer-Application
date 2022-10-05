package com.app.id.pemmob

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.app.id.pemmob.model.biodata

class adater : BaseAdapter{
    private var notesList = ArrayList<biodata>()
    private var context: Context? = null

    constructor(context: Context, notesList: ArrayList<biodata>) : super() {
        this.notesList = notesList
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val view: View?
        val vh: ViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(context)

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_data, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
            Log.i("JSA", "set Tag for ViewHolder, position: " + position)
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        var mNote = notesList[position]

        vh.tvId.text = mNote.id.toString()
        vh.tvNim.text = mNote.nim
        vh.tvNama.text = mNote.nama
        return view
    }

    override fun getItem(position: Int): Any {
        return notesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return notesList.size
    }

    private class ViewHolder(view: View?) {
        val tvId: TextView
        val tvNim: TextView
        val tvNama: TextView

        init {
            this.tvId = view?.findViewById(R.id.tvId) as TextView
            this.tvNim = view?.findViewById(R.id.tvNim) as TextView
            this.tvNama = view?.findViewById(R.id.tvNama) as TextView
        }
    }
}