package com.yaqubabbasov.noteapp.ui.home.home_contract

import com.yaqubabbasov.noteapp.data.local.entity.Note

data class HomeState (
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
    val error: String? = null,
    val word: String = "",
    val isEmpty:Boolean=true


)