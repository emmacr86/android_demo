package com.emmanuel.demo_nyt.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Class for the settings table
@Entity(tableName = "settings")
data class Setting(
    @PrimaryKey
    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "VALUE")
    val value: String?
)