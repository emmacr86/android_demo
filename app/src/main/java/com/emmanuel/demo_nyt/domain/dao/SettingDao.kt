package com.emmanuel.demo_nyt.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emmanuel.demo_nyt.domain.model.Setting

//Class Dao with all the queries for the settings table
@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(setting: Setting)

    @Query("SELECT * FROM settings WHERE name = :name")
    suspend fun getSettingByName(name: String): Setting?
}