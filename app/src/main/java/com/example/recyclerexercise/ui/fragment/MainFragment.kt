package com.example.recyclerexercise.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recyclerexercise.MainActivity
import com.example.recyclerexercise.R
import com.example.recyclerexercise.adapter.GithubUserListAdapter
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.example.recyclerexercise.data.service.GithubUserService
import com.example.recyclerexercise.data.source.GithubUserDataSource
import com.example.recyclerexercise.databinding.FragmentMainBinding
import com.example.recyclerexercise.viewmodel.GithubUserVIewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private lateinit var sGithubUserService: GithubUserService
private lateinit var sGithubUserDataSource: GithubUserDataSource
private lateinit var sGithubUserRepository: GithubUserRepository

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    companion object{
        const val TAG: String = "MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var githubUserRepository: GithubUserRepository
    private lateinit var githubUserVIewModel: GithubUserVIewModel
    private val githubUserListAdapter: GithubUserListAdapter by lazy {
        GithubUserListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initModel()
        initViewModel()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvUserList.adapter = githubUserListAdapter
        githubUserVIewModel.loadGithubUsers()
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
            githubUsers.observe(this@MainFragment) {
                githubUserListAdapter.submitList(it)
            }
        }
    }
}