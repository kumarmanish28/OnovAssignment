package com.mktech.onovassignment.data.repository

import com.mktech.onovassignment.data.api.ApiService
import com.mktech.onovassignment.util.ResultState
import com.mktech.onovassignment.util.Utility.toUserMessage
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CharacterRepository {

    override suspend fun getCharacters() = flow {
        emit(ResultState.Loading)
        try {
            val response = api.getCharacters()
            emit(ResultState.Success(response.results))
        } catch (e: Exception) {
            emit(ResultState.Error(e.toUserMessage()))
        }
    }

    override suspend fun getCharacterDetail(id: Int) = flow {
        emit(ResultState.Loading)
        try {
            val response = api.getCharacterDetail(id)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.toUserMessage()))
        }
    }
}
