package com.yaqubabbasov.noteapp.data.repository

import com.yaqubabbasov.noteapp.data.datasource.LocalDataSource
import com.yaqubabbasov.noteapp.data.local.entity.Note
import javax.inject.Inject

class Repository @Inject constructor(val localDataSource: LocalDataSource) {
    suspend  fun getAllNotes(): List<Note> = localDataSource.getAllNotes()
    suspend fun save(title: String, content: String) = localDataSource.save(title, content)
    suspend fun delete(id: Int) = localDataSource.delete(id)
    suspend fun update(id: Int, title: String, content: String) = localDataSource.update(id, title, content)
    suspend fun search(word: String): List<Note> = localDataSource.search(word)




}