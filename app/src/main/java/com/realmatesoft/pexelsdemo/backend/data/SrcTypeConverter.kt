package com.realmatesoft.pexelsdemo.backend.data

import androidx.room.TypeConverter
import com.google.gson.Gson

//Type converter for Room
//Needed for saving objects of our custom Src plain old kotlin object
class SrcTypeConverter {

    @TypeConverter
    fun srcToString(src: Src): String = Gson().toJson(src)

    @TypeConverter
    fun stringToSrc(string: String): Src = Gson().fromJson(string, Src::class.java)

}