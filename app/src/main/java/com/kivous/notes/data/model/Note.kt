package com.kivous.notes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val note: String? = null,
    val userId: String? = null
)