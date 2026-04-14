package com.yaqubabbasov.noteapp.ui.update.update_contract

import com.yaqubabbasov.noteapp.data.local.entity.Note

sealed interface UpdateIntent {
    data class UpdateItem(val id: Int,
                          val title: String,
                          val content: String) : UpdateIntent
    data class LoadItem(val args: Note): UpdateIntent


}