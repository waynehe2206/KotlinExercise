package com.example.recyclerexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GithubUserViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {
    companion object {
        private const val TAG = "GithubUserViewModel"
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

    fun loadGithubUsers(since: Int = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val githubUserList = githubUserRepository.getUsers(since)
            viewModelScope.launch(Dispatchers.Main){
                _githubUsers.value = githubUserList
            }
        }
    }
}