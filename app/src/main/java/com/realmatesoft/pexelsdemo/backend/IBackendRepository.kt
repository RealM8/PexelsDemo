package com.realmatesoft.pexelsdemo.backend

import com.realmatesoft.pexelsdemo.backend.data.Photo
import io.reactivex.Single

interface IBackendRepository {

    fun getPhotosFromPexels() : Single<List<Photo>>

    fun getSpecificPhotoFromPexels(pictureId : Int, fromLocal : Boolean) : Single<Photo>

}