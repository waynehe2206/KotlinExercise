package com.example.recyclerexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.recyclerexercise.adapter.GithubUserListAdapter
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.example.recyclerexercise.data.service.GithubUserService
import com.example.recyclerexercise.data.source.GithubUserDataSource
import com.example.recyclerexercise.databinding.ActivityMainBinding
import com.example.recyclerexercise.viewmodel.GithubUserVIewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private lateinit var sGithubUserService: GithubUserService
private lateinit var sGithubUserDataSource: GithubUserDataSource
private lateinit var sGithubUserRepository: GithubUserRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var githubUserVIewModel: GithubUserVIewModel
    private val githubUserListAdapter: GithubUserListAdapter by lazy {
        GithubUserListAdapter()
    }

    companion object{
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initModel()
        initViewModel()
        observeData()

        githubUserVIewModel.loadGithubUsers()
    }

    private fun initUI() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUserList.adapter = githubUserListAdapter
    }

    private fun initModel(){
        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()

        sGithubUserService = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(okHttpClient)
                .build()
                .create(GithubUserService::class.java)
        sGithubUserDataSource = GithubUserDataSource(sGithubUserService)
        sGithubUserRepository = GithubUserRepository(sGithubUserDataSource)

        githubUserRepository = sGithubUserRepository
    }

    private fun initViewModel(){
        githubUserVIewModel = GithubUserVIewModel(githubUserRepository)
    }

    private fun observeData(){
        githubUserVIewModel.apply {
            githubUsers.observe(this@MainActivity) {
                githubUserListAdapter.submitList(it)
            }
        }
    }
}