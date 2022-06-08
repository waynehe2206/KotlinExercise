package com.example.recyclerexercise.data.source

import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.data.service.GithubUserService

class GithubUserDataSource(val githubUserService: GithubUserService) {

    companion object {
        const val TAG: String = "GithubUserDataSource"
    }

    suspend fun getUsers(): List<GithubUser>? {
        val response = githubUserService.getUsers()
        var result: List<GithubUser>? = null
        if (response.isSuccessful) {
            response.body()?.let {
                result = it
            }
        }
        return result
    }

    suspend fun getUsers(since: Int = 0): List<GithubUser>? {
        val response = githubUserService.getUsers(since)
        var result: List<GithubUser>? = null
        if (response.isSuccessful) {
            response.body()?.let {
                result = it
            }
        }
        return result
    }
}