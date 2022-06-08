package com.example.recyclerexercise.di

import com.example.recyclerexercise.data.Constant
import com.example.recyclerexercise.data.repository.GithubUserRepository
import com.example.recyclerexercise.data.service.GithubUserService
import com.example.recyclerexercise.data.source.GithubUserDataSource
import com.example.recyclerexercise.viewmodel.GithubUserViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClientModules = module {
    single {
        OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()
    }
}

val retrofitModules = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constant.GITHUB_API_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(get())
            .build()
    }
}

val apiServiceModules = module {
    single { provideGithubUserService(get()) }
}

val dataSourceModules = module {
    single { GithubUserDataSource(get()) }
}

val repositoryModules = module {
    single { GithubUserRepository(get()) }
}

val viewModelModules = module {
    viewModel { GithubUserViewModel(get()) }
}

fun provideGithubUserService(retrofit: Retrofit): GithubUserService =
    retrofit.create(GithubUserService::class.java)
