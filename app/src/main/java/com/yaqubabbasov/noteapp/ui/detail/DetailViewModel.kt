package com.yaqubabbasov.noteapp.ui.detail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaqubabbasov.noteapp.data.repository.Repository
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailIntent
import com.yaqubabbasov.noteapp.ui.detail.detail_contracts.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: Repository): ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state = _state.asStateFlow()
    fun intent(intent: DetailIntent){
        when(intent){
            is DetailIntent.SaveItem -> buttonClicked(intent.title, intent.content)
            is DetailIntent.TitleChanged -> _state.value = _state.value.copy(title = intent.title)
            is DetailIntent.ContentChanged -> _state.value = _state.value.copy(content = intent.content)



        }


    }

    fun buttonClicked(title: String, content: String){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true,
                error = null,isSaved = false)
            try {
                repository.save(title, content)
                _state.value = _state.value.copy(isLoading = false,
                    error = null,title=title,content=content, isSaved = true)
            }catch (e: Exception){
                _state.value = _state.value.copy(isLoading = true ,
                    error = e.message,isSaved = false)


            }

        }



    }

}