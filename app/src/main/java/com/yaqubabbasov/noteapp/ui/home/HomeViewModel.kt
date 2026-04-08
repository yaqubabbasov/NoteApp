package com.yaqubabbasov.noteapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaqubabbasov.noteapp.data.repository.Repository
import com.yaqubabbasov.noteapp.ui.home.home_contract.HomeIntent
import com.yaqubabbasov.noteapp.ui.home.home_contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state = _state.asStateFlow()

    init {
        intent(HomeIntent.LoadItem)
    }

    fun intent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadItem -> loadItem()
            is HomeIntent.DeleteItem -> delete(intent.id)
            is HomeIntent.WordChanged -> search(intent.word)

        }
    }


    private fun loadItem() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
            )
            try {
                val result = repository.getAllNotes()
                _state.value = _state.value.copy(
                    isLoading = false,
                    notes = result,
                    error = null,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = e.message,
                )

            }

        }
    }
    fun delete(id: Int){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
            )
            try {
                repository.delete(id)
                _state.value=_state.value.copy(
                    isLoading = false,
                    notes = _state.value.notes.filter { it.id!=id },
                    error = null,
                )


            }catch (e: Exception){
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = e.message,
                )
            }

        }


    }
    private fun search(word: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null)
            try {
               val result= repository.search(word)
                _state.value = _state.value.copy(
                    isLoading = false,
                    word = word,
                    notes=result,
                    error = null

                )
            }catch (e: Exception){
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?:"")

            }

        }
    }


}



