package com.yaqubabbasov.noteapp.ui.update.update_contract

import com.yaqubabbasov.noteapp.data.local.entity.Note

data class UpdateState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val title: String = "",
    val content: String = "",
    val isUpdate: Boolean = false,



)