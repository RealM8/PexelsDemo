package com.realmatesoft.pexelsdemo.backend.datasources.remote

import com.realmatesoft.pexelsdemo.backend.data.PexelsResponseBodyWithList
import com.realmatesoft.pexelsdemo.backend.data.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IRemoteServerClient {

    @GET("search?")
    fun searchAndGetPictures(@Query("query") query: String, @Query("per_page") amountOfPics: Int): Single<PexelsResponseBodyWithList>

    @GET("photos/{id}")
    fun getSpecificPicture(@Path("id") pictureId: Int): Single<Photo>

}