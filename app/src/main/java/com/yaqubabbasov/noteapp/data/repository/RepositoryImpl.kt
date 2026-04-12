package com.yaqubabbasov.noteapp.data.repository

import com.yaqubabbasov.noteapp.data.datasource.LocalDataSource
import com.yaqubabbasov.noteapp.data.domain.Repository
import com.yaqubabbasov.noteapp.data.local.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val localDataSource: LocalDataSource): Repository {
    override suspend fun getAllNotes(): List<Note> =
        withContext(Dispatchers.IO){
            return@withContext localDataSource.getAllNotes()
        }


    override suspend fun save(title: String, content: String) {
        localDataSource.save(title, content)
    }

    override suspend fun delete(id: Int){
        localDataSource.delete(id)
    }

    override suspend fun update(id: Int, title: String, content: String) {
        localDataSource.update(id, title, content)
    }

    override suspend fun search(word: String): List<Note> =
        withContext(Dispatchers.IO){
            return@withContext localDataSource.search(word)
        }



}