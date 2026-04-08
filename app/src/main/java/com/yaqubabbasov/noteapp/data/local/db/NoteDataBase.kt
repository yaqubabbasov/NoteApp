package com.yaqubabbasov.noteapp.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yaqubabbasov.noteapp.data.local.dao.NoteDao
import com.yaqubabbasov.noteapp.data.local.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}