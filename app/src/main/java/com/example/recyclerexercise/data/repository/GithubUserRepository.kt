package com.example.recyclerexercise.data.repository

import android.util.Log
import com.example.recyclerexercise.data.model.GithubUser
import com.example.recyclerexercise.data.source.GithubUserDataSource

class GithubUserRepository(val dataSource: GithubUserDataSource) {
    companion object {
        private const val TAG = "GithubUserRepository"
    }

    suspend fun getUsers(): List<GithubUser>{
        val userList = ArrayList<GithubUser>()
        try {
            dataSource.getUsers()?.let {
                userList.addAll(it)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getUsers exception: $e")
        }

        return userList
    }

    suspend fun getUsers(since: Int = 0): List<GithubUser>{
        val userList = ArrayList<GithubUser>()
        try {
            dataSource.getUsers(since)?.let {
                userList.addAll(it)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getUsers exception: $e")
        }
        return userList
    }
}