package com.kivous.notes.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kivous.notes.data.model.Note
import com.kivous.notes.databinding.ListNoteBinding
import javax.inject.Inject

class NoteAdapter(private val notesViewController: (ViewHolder, Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListNoteBinding) : RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, Comparator)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = differ.currentList[position]
        notesViewController(holder, note)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}


object Comparator : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
        oldItem == newItem

}