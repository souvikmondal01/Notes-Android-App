package com.kivous.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kivous.notes.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes where userId = :userId")
    fun getAllNotes(userId: String): Flow<List<Note>>

    @Query("SELECT COUNT(*) FROM notes where userId = :userId")
    fun getNotesCount(userId: String): Flow<Int>

}