package com.mktech.onovassignment.data.repository

import com.mktech.onovassignment.data.model.Character
import com.mktech.onovassignment.util.ResultState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
     suspend  fun getCharacters(): Flow<ResultState<List<Character>>>
    suspend  fun getCharacterDetail(id: Int): Flow<ResultState<Character>>
}