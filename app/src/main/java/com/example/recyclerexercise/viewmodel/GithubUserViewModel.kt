package com.example.recyclerexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GithubUserViewModel(private val githubUserRepository: GithubUserRepository): ViewModel() {
    companion object {
        private const val TAG = "GithubUserViewModel"
    }

    private var isGithubUsersRefreshing = false

    private val _scrollListToTop = LiveEvent<Unit>()
    val scrollListToTop: LiveData<Unit> = _scrollListToTop

    private var isListRefreshingFlag = false
    val isListRefreshing: MutableLiveData<Boolean> = MutableLiveData(isListRefreshingFlag)

    private val _githubUsers = LiveEvent<ArrayList<GithubUser>>()
    val githubUsers: LiveData<ArrayList<GithubUser>> = _githubUsers

    fun loadGithubUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            val githubUserList = githubUserRepository.getUsers()
            viewModelScope.launch(Dispatchers.Main){
                _githubUsers.value = ArrayList(githubUserList)
            }
        }
    }

    fun loadGithubUsers(since: Int = 0){
        viewModelScope.launch(Dispatchers.IO) {
            val githubUserList = githubUserRepository.getUsers(since)
            viewModelScope.launch(Dispatchers.Main){
                _githubUsers.value?.let {
                    val newUserList = ArrayList(it)
                    newUserList.addAll(githubUserList)
                    _githubUsers.value = newUserList
                }
            }
        }
    }

    fun refreshGithubUsers(needScrollToTop: Boolean = false){
        if (isGithubUsersRefreshing) return
        isGithubUsersRefreshing = true
        setListRefreshing(true)

        viewModelScope.launch(Dispatchers.IO){
            loadGithubUsers(0)

            if (needScrollToTop){
                scrollListToTop()
            }
            setListRefreshing(false)
            isGithubUsersRefreshing = false
        }
    }

    fun setListRefreshing(isRefreshing : Boolean){
        if (isListRefreshingFlag != isRefreshing){
            isListRefreshingFlag = isRefreshing
            viewModelScope.launch(Dispatchers.Main) {
                isListRefreshing.value = isRefreshing
            }
        }
    }

    fun scrollListToTop(){
        viewModelScope.launch(Dispatchers.Main) {
            delay(500)
            _scrollListToTop.value = Unit
        }
    }

    fun getLastUserId(): Int{
        val lastUserId = _githubUsers.value?.let {
            it[it.size-1].id
        }

        lastUserId?.let {
            return it
        }?: return 0
    }
}