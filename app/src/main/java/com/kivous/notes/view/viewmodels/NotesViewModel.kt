package com.kivous.notes.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kivous.notes.data.model.Note
import com.kivous.notes.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    fun createNote(note: Note) = viewModelScope.launch {
        notesRepository.createNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        notesRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        notesRepository.deleteNote(note)
    }

    fun getAllNotes(userId: String) = notesRepository.getAllNotes(userId)

    fun isNoteListEmpty(userId: String): Flow<Boolean> = notesRepository.isNoteListEmpty(userId)

}