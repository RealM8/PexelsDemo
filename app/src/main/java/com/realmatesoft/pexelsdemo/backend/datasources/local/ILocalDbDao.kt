package com.realmatesoft.pexelsdemo.backend.datasources.local

import androidx.room.*
import com.realmatesoft.pexelsdemo.backend.data.Photo
import io.reactivex.Single

@Dao
interface ILocalDbDao {

    @Query("SELECT * FROM photos")
    fun getAll() : Single<List<Photo>>

    //Replaces old photo with newer photo if their IDs are same
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDownloadedPhotos(photos: List<Photo>)

    @Query("SELECT * FROM photos WHERE id == :id")
    fun getSpecificPhoto (id : Int) : Single<Photo>

    @Update
    fun updateDownloadedPhoto(photo : Photo)

}