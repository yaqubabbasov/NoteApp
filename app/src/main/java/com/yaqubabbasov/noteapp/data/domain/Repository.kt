package com.yaqubabbasov.noteapp.data.domain

import android.content.res.Resources
import com.yaqubabbasov.noteapp.data.local.entity.Note

interface Repository {
    suspend  fun getAllNotes(): List<Note>
    suspend fun save(title: String, content: String)
    suspend fun delete(id: Int)
    suspend fun update(id: Int, title: String, content: String)
    suspend fun search(word: String): List<Note>
}