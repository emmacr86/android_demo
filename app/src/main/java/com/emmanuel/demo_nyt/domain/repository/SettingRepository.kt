package com.emmanuel.demo_nyt.domain.repository

import com.emmanuel.demo_nyt.DemoApplication
import com.emmanuel.demo_nyt.domain.model.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Repository class with context Dispatchers IO for insert and get the settings from the database
//The classes are open to be overridden in the tests
open class SettingsRepository {

    open suspend fun insertSetting(setting: Setting): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                DemoApplication.database.settingDao().insertOrUpdate(setting)
                true // Return true if the operation was successful
            } catch (e: Exception) {
                e.printStackTrace()
                false // Return false if there was an error
            }
        }
    }

    open suspend fun getSettingByName(settingName: String): Setting? {
        return withContext(Dispatchers.IO) {
            DemoApplication.database.settingDao().getSettingByName(settingName)
        }
    }

}