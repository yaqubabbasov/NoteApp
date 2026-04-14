package com.yaqubabbasov.noteapp.di

import android.content.Context
import androidx.room.Room
import com.yaqubabbasov.noteapp.data.datasource.LocalDataSource
import com.yaqubabbasov.noteapp.data.local.dao.NoteDao
import com.yaqubabbasov.noteapp.data.local.db.NoteDataBase
import com.yaqubabbasov.noteapp.data.repository.NoteNoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDao(@ApplicationContext context: Context): NoteDao{
        val vt= Room.databaseBuilder(context, NoteDataBase::class.java,"NoteTable").build()
        return vt.noteDao()

    }
    @Provides
    @Singleton
    fun provideDataSource(dao: NoteDao): LocalDataSource{
        return LocalDataSource(dao)
    }
    @Provides
    @Singleton
    fun provideRepositoryImpl(ds: LocalDataSource): NoteNoteRepositoryImpl {
        return NoteNoteRepositoryImpl(ds)
    }






}