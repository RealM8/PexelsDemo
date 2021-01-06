package com.realmatesoft.pexelsdemo.backend.provider

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpClientProvider {

    fun get() : OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(getAuthorizationInterceptor())
                .build()

    private fun getAuthorizationInterceptor() : Interceptor = Interceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer 563492ad6f91700001000001603e0c38df544cacb6616bd9bfafa82f")
                .build()
        chain.proceed(newRequest)
    }

}