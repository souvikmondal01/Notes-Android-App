package com.kivous.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kivous.notes.data.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}