package com.yaqubabbasov.noteapp.ui.home.home_contract

import com.yaqubabbasov.noteapp.data.local.entity.Note

sealed interface HomeIntent {
   data  object LoadItem: HomeIntent
   data class DeleteItem(val id: Int): HomeIntent
   data class WordChanged(val word: String): HomeIntent
}




