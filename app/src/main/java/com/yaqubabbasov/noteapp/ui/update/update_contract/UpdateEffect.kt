package com.yaqubabbasov.noteapp.ui.update.update_contract

interface UpdateEffect {
    data class ShowToast(val message: String) : UpdateEffect
    object NavigateBack : UpdateEffect


}