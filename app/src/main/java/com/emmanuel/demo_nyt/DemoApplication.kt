package com.emmanuel.demo_nyt

import android.app.Application
import androidx.room.Room
import com.emmanuel.demo_nyt.domain.AppDatabase

class DemoApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "gb-database").build()
    }
}