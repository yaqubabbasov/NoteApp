package com.yaqubabbasov.noteapp.ui.detail.detail_contracts

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val title: String = "",
    val content: String = "",
    val isSaved: Boolean = false



)