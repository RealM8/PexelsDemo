package com.realmatesoft.pexelsdemo

import android.app.Application
import androidx.room.Room
import com.realmatesoft.pexelsdemo.backend.BackendRepositoryImpl
import com.realmatesoft.pexelsdemo.backend.IBackendRepository
import com.realmatesoft.pexelsdemo.backend.datasources.remote.IRemoteServerClient
import com.realmatesoft.pexelsdemo.backend.utils.ConnectionChecker

class PexelsDemoApplication : Application() {

    companion object {
        lateinit var instance: PexelsDemoApplication
            private set
    }

    val remoteServerClient : IRemoteServerClient by lazy {
        IRemoteServerClient.create()
    }

    val connectionChecker by lazy { ConnectionChecker(baseContext) }
    val pexelsDemoDb by lazy { Room.databaseBuilder(applicationContext, PexelsDemoDatabase::class.java, "pexels-demo-cache").build()}
    val backendRepository : IBackendRepository by lazy { BackendRepositoryImpl(remoteServerClient, pexelsDemoDb.localDbDao(), connectionChecker) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}