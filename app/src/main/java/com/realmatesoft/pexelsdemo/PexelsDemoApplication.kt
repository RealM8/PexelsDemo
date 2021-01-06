package com.realmatesoft.pexelsdemo

import android.app.Application
import androidx.room.Room
import com.realmatesoft.pexelsdemo.backend.BackendRepositoryImpl
import com.realmatesoft.pexelsdemo.backend.IBackendRepository
import com.realmatesoft.pexelsdemo.backend.provider.ApiServiceProvider
import com.realmatesoft.pexelsdemo.backend.provider.OkHttpClientProvider
import com.realmatesoft.pexelsdemo.backend.utils.ConnectionChecker
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PexelsDemoApplication : Application() {

    lateinit var dependencies: DI

    companion object {
        lateinit var instance: PexelsDemoApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dependencies = DI(this)
    }
}