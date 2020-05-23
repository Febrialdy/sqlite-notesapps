package com.adityafebrialdy.sqlitenoteapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityafebrialdy.sqlitenoteapp.CustomOnItemClickListener
import com.adityafebrialdy.sqlitenoteapp.Entity.Note
import com.adityafebrialdy.sqlitenoteapp.NoteAddUpdateActivity
import com.adityafebrialdy.sqlitenoteapp.R
import kotlinx.android.synthetic.main.item_note.view.*


class NoteAdapter(private val activity: Activity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

  var listNotes = ArrayList<Note>()
      set(listNotes){
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        notifyDataSetChanged()
      }

  fun addItem(note: Note){
    this.listNotes.add(note)
    notifyItemInserted(this.listNotes.size - 1)
  }

  fun updateItem(pos: Int, note: Note){
    this.listNotes[pos] = note
    notifyItemChanged(pos, note)
  }

  fun removeItem(pos: Int){
    this.listNotes.removeAt(pos)
    notifyItemRemoved(pos)
    notifyItemRangeChanged(pos, this.listNotes.size)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    return NoteViewHolder(view)
  }

  override fun getItemCount(): Int = this.listNotes.size

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    holder.bind(listNotes[position], activity)
  }

  class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(note: Note, activity: Activity) {
      with(itemView){
        tv_item_title.text = note.title
        tv_item_date.text = note.date
        tv_item_description.text = note.description
        cv_item_note.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
          override fun onItemClicked(view: View, position: Int) {
            val intent = Intent(activity, NoteAddUpdateActivity::class.java)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
            activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
          }
        }))
      }
    }
  }
}