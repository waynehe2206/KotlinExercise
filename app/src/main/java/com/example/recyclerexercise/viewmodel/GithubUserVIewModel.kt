package com.example.recyclerexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubUserVIewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {
    companion object {
        private const val TAG = "GithubUserVIewModel"
    }

    private val _githubUsers = LiveEvent<List<GithubUser>>()
    val githubUsers: LiveData<List<GithubUser>> = _githubUsers

    fun loadGithubUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            val githubUserList = githubUserRepository.getUsers()
            viewModelScope.launch(Dispatchers.Main){
                _githubUsers.value = githubUserList
            }
        }
    }
}