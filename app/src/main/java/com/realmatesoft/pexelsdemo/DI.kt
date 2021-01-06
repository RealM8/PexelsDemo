package com.realmatesoft.pexelsdemo

import android.content.Context
import androidx.room.Room
import com.realmatesoft.pexelsdemo.backend.BackendRepositoryImpl
import com.realmatesoft.pexelsdemo.backend.IBackendRepository
import com.realmatesoft.pexelsdemo.backend.provider.ApiServiceProvider
import com.realmatesoft.pexelsdemo.backend.provider.OkHttpClientProvider
import com.realmatesoft.pexelsdemo.backend.utils.ConnectionChecker
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DI (context : Context) {

    val okHttpClient by lazy { OkHttpClientProvider().get() }
    val gson by lazy { GsonConverterFactory.create() }
    val rxJava2CallAdapter by lazy { RxJava2CallAdapterFactory.create() }
    val remoteServerClient by lazy { ApiServiceProvider(okHttpClient, gson, rxJava2CallAdapter).getApiService() }

    val connectionChecker by lazy { ConnectionChecker(context) }
    val pexelsDemoDb by lazy { Room.databaseBuilder(context, PexelsDemoDatabase::class.java, "pexels-demo-cache").build()}
    val backendRepository : IBackendRepository by lazy { BackendRepositoryImpl(remoteServerClient, pexelsDemoDb.localDbDao(), connectionChecker) }

}