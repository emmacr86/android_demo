package com.emmanuel.demo_nyt.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emmanuel.demo_nyt.domain.dao.SettingDao
import com.emmanuel.demo_nyt.domain.model.Setting

//Class for the database
@Database(entities = [Setting::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingDao(): SettingDao
}