package com.realmatesoft.pexelsdemo.backend.provider

import com.google.gson.Gson
import com.realmatesoft.pexelsdemo.backend.datasources.remote.IRemoteServerClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceProvider(private val okHttpClient: OkHttpClient, private val gson: GsonConverterFactory, private val rxJava2CallAdapter : RxJava2CallAdapterFactory) {

    val baseUrl = "https://api.pexels.com/v1/"

    fun getApiService() = Retrofit.Builder()
            .addConverterFactory(gson)
            .addCallAdapterFactory(rxJava2CallAdapter)
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(IRemoteServerClient::class.java)

}