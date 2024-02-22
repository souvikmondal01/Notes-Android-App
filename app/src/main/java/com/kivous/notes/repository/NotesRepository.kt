package com.kivous.notes.repository

import com.kivous.notes.data.db.NoteDatabase
import com.kivous.notes.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val db: NoteDatabase
) {
    suspend fun createNote(note: Note) = db.getNoteDao().createNote(note)

    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes(userId: String) = db.getNoteDao().getAllNotes(userId)

    fun isNoteListEmpty(userId: String): Flow<Boolean> =
        db.getNoteDao().getNotesCount(userId).map { it == 0 }
}