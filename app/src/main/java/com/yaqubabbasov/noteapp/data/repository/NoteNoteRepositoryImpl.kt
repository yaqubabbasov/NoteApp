package com.yaqubabbasov.noteapp.data.repository

import com.yaqubabbasov.noteapp.data.datasource.NoteLocalDataSource
import com.yaqubabbasov.noteapp.data.domain.NoteRepository
import com.yaqubabbasov.noteapp.data.local.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteNoteRepositoryImpl @Inject constructor(val noteLocalDataSource: NoteLocalDataSource): NoteRepository {
    override suspend fun getAllNotes(): List<Note> =
        withContext(Dispatchers.IO){
            return@withContext noteLocalDataSource.getAllNotes()
        }


    override suspend fun save(title: String, content: String) {
        noteLocalDataSource.save(title, content)
    }

    override suspend fun delete(id: Int){
        noteLocalDataSource.delete(id)
    }

    override suspend fun update(id: Int, title: String, content: String) {
        noteLocalDataSource.update(id, title, content)
    }

    override suspend fun search(word: String): List<Note> =
        withContext(Dispatchers.IO){
            return@withContext noteLocalDataSource.search(word)
        }



}