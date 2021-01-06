package com.realmatesoft.pexelsdemo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.realmatesoft.pexelsdemo.backend.data.Photo
import com.realmatesoft.pexelsdemo.backend.data.SrcTypeConverter
import com.realmatesoft.pexelsdemo.backend.datasources.local.ILocalDbDao

@TypeConverters(SrcTypeConverter::class)
@Database(entities = arrayOf(Photo::class), version = 1)
abstract class PexelsDemoDatabase : RoomDatabase() {
    abstract fun localDbDao(): ILocalDbDao
}