package com.yaqubabbasov.noteapp.ui.detail.detail_contracts

sealed interface DetailIntent {
    data class SaveItem(val title: String, val content: String) : DetailIntent
    data class TitleChanged(val title: String) : DetailIntent
    data class ContentChanged(val content: String) : DetailIntent
}