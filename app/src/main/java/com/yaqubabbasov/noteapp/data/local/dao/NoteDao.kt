package com.yaqubabbasov.noteapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yaqubabbasov.noteapp.data.local.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAllNotes(): List<Note>

    @Insert
    suspend fun save(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Update
    suspend fun update(note: Note)
    @Query("SELECT * FROM note WHERE title LIKE '%' || :word || '%' OR content LIKE '%' || :word || '%'")
    suspend fun search(word: String): List<Note>
}





