package com.mktech.onovassignment.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mktech.onovassignment.data.repository.CharacterRepository
import com.mktech.onovassignment.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mktech.onovassignment.data.model.Character

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<List<Character>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Character>>> = _state

    fun fetchCharacters() {
        viewModelScope.launch {
            characterRepository.getCharacters().collect {
                _state.value = it
            }
        }
    }
}