package com.example.simplenoteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenoteapp.R
import com.example.simplenoteapp.entities.Notes
import kotlinx.android.synthetic.main.item_notes.view.*

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder> () {

    var listener: OnItemClickListener? = null
    var arrList = ArrayList<Notes>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNewList: List<Notes>) {
        arrList = arrNewList as ArrayList<Notes>
    }

    fun setOnClickListener(listenerInt:OnItemClickListener) {
        listener = listenerInt
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.itemView.textTitle.text = arrList[position].title
        holder.itemView.textDesc.text = arrList[position].noteText
        holder.itemView.textDateTime.text = arrList[position].dateTime

        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    interface OnItemClickListener {
        fun onClicked(noteId:Int)
    }
}