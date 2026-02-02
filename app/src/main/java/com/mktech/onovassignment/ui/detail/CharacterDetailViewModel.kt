package com.mktech.onovassignment.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mktech.onovassignment.data.model.Character
import com.mktech.onovassignment.data.repository.CharacterRepository
import com.mktech.onovassignment.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<Character>>(ResultState.Loading)
    val state: StateFlow<ResultState<Character>> = _state

    fun fetchCharacterDetails(id: Int) {
        viewModelScope.launch {
            characterRepository.getCharacterDetail(id = id).collect {
                _state.value = it
            }
        }
    }
}