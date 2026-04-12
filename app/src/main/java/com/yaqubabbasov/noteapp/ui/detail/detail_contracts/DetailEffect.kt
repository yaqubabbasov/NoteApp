package com.yaqubabbasov.noteapp.ui.detail.detail_contracts

sealed interface DetailEffect {
    data class ShowToast(val message: String) : DetailEffect
    object NavigateBack : DetailEffect
}