package com.yaqubabbasov.noteapp.ui.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaqubabbasov.noteapp.data.domain.Repository
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateEffect
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateIntent
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val _state = MutableStateFlow(UpdateState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<UpdateEffect>()
    val effect = _effect.asSharedFlow()
    fun intent(intent: UpdateIntent) {
        when (intent) {
            is UpdateIntent.UpdateItem -> update(intent.id, intent.title, intent.content)

        }
    }

    fun update(id: Int, title: String, content: String) {
        viewModelScope.launch {
            if (title.isBlank() || content.isBlank()) {
                _effect.emit(UpdateEffect.ShowToast("Title and content cannot be empty"))
                return@launch
            }
            _state.value = _state.value.copy(
                isLoading = true,
                error = null, isUpdate = false
            )
            try {
                _state.value = _state.value.copy(isLoading = true,
                    error = null,isUpdate = false)
                repository.update(id, title, content)
                _effect.emit(UpdateEffect.ShowToast("Update"))
                _effect.emit(UpdateEffect.NavigateBack)


            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = e.message, isUpdate = false
                )
            }


        }
    }
}