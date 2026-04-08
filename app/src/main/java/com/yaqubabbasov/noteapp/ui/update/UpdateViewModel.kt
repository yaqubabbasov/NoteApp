package com.yaqubabbasov.noteapp.ui.update

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaqubabbasov.noteapp.data.repository.Repository
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateIntent
import com.yaqubabbasov.noteapp.ui.update.update_contract.UpdateState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val _state = MutableStateFlow(UpdateState())
    val state = _state.asStateFlow()
    fun intent(intent: UpdateIntent) {
        when (intent) {
            is UpdateIntent.UpdateItem -> update(intent.id, intent.title, intent.content)

        }
    }

    fun update(id: Int, title: String, content: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null, isUpdate = false
            )
            try {
                repository.update(id, title, content)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = null, title = title, content = content, isUpdate = true
                )


            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = e.message, isUpdate = false
                )
            }


        }
    }
}