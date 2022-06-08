package com.example.recyclerexercise

import android.app.Application
import android.content.Context
import com.example.recyclerexercise.di.*
import org.koin.core.context.GlobalContext.startKoin

class ExerciseApplication: Application() {
    companion object {
        const val TAG = "ExerciseApplication"
        private lateinit var instance: ExerciseApplication

        internal fun getInstance(): ExerciseApplication {
            return instance
        }

        internal fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(
                listOf(
                    okHttpClientModules,
                    retrofitModules,
                    apiServiceModules,
                    dataSourceModules,
                    repositoryModules,
                    viewModelModules
                )
            )
        }
    }

}