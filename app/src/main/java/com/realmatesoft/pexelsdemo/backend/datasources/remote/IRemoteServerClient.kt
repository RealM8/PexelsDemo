package com.realmatesoft.pexelsdemo.backend.datasources.remote

import com.google.gson.GsonBuilder
import com.realmatesoft.pexelsdemo.backend.data.PexelsResponseBodyWithList
import com.realmatesoft.pexelsdemo.backend.data.Photo
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IRemoteServerClient {

    companion object {
        fun create(): IRemoteServerClient {
            val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer 563492ad6f91700001000001603e0c38df544cacb6616bd9bfafa82f")
                    .build()
                chain.proceed(newRequest)
            }.build()
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.pexels.com/v1/")
                .build()

            return retrofit.create(IRemoteServerClient::class.java)
        }
    }

    @GET("search?")
    fun searchAndGetPictures(@Query("query") query: String, @Query("per_page") amountOfPics: Int): Single<PexelsResponseBodyWithList>

    @GET("photos/{id}")
    fun getSpecificPicture(@Path("id") pictureId: Int): Single<Photo>

}