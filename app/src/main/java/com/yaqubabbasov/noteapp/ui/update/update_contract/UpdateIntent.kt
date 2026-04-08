package com.yaqubabbasov.noteapp.ui.update.update_contract

sealed interface UpdateIntent {
    data class UpdateItem(val id: Int,
                          val title: String,
                          val content: String) : UpdateIntent


}