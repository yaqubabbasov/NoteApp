package com.yaqubabbasov.noteapp.data.datasource

import com.yaqubabbasov.noteapp.data.local.dao.NoteDao
import com.yaqubabbasov.noteapp.data.local.entity.Note
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LocalDataSource @Inject constructor(private val noteDao: NoteDao) {
    suspend fun getAllNotes(): List<Note> =
        withContext(Dispatchers.IO) {
            delay(1000L)
            return@withContext noteDao.getAllNotes()
        }

    suspend fun save(title: String, content: String){
        delay(1000L)
        val note = Note(0, title, content)
        noteDao.save(note)
    }
    suspend fun delete(id: Int){
        noteDao.delete(Note(id, "", ""))
    }
    suspend fun update(id: Int, title: String, content: String){
        delay(2000L)
        noteDao.update(Note(id, title, content))
    }
    suspend fun search(word: String): List<Note> =
        withContext(Dispatchers.IO) {
            return@withContext noteDao.search(word)
        }

    }




