package com.yaqubabbasov.noteapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaqubabbasov.noteapp.data.domain.NoteRepository
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailEffect
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailIntent
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val noteRepository: NoteRepository): ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<DetailEffect>()
    val effect = _effect.asSharedFlow()
    fun intent(intent: DetailIntent){
        when(intent){
            is DetailIntent.SaveItem -> buttonClicked(intent.title, intent.content)
            is DetailIntent.TitleChanged -> _state.value = _state.value.copy(title = intent.title)
            is DetailIntent.ContentChanged -> _state.value = _state.value.copy(content = intent.content)



        }
    }



    fun buttonClicked(title: String, content: String){
        viewModelScope.launch {
            if (title.isBlank() || content.isBlank()) {
               _effect.emit(DetailEffect.ShowToast("Title and content cannot be empty"))
                return@launch
            }
            try {
                _state.value = _state.value.copy(isLoading = true,
                    error = null,isSaved = false)
                noteRepository.save(title, content)
                _effect.emit(DetailEffect.ShowToast("Saved"))
                _effect.emit(DetailEffect.NavigateBack)
            }catch (e: Exception){
                _state.value = _state.value.copy(isLoading = false,
                    error = e.message,isSaved = false)

            }

        }



    }

}