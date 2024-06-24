package com.emmanuel.demo_nyt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.emmanuel.demo_nyt.domain.model.Setting
import com.emmanuel.demo_nyt.domain.repository.SettingsRepository
import kotlinx.coroutines.launch

//View model for save the api key in the Settings screen
class SettingViewModel : ViewModel() {

    private val _onSaveResult = MutableLiveData<OnSaveResult?>(null)
    val onSaveResult: LiveData<OnSaveResult?> get() = _onSaveResult

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            val setting = Setting(name = "api_key", value = apiKey)
            val settingsRepository = SettingsRepository()
            if (settingsRepository.insertSetting(setting)) {
                _onSaveResult.value = OnSaveResult.Success
            } else {
                _onSaveResult.value =
                    OnSaveResult.Error("An error occurred while saving the api key")
            }
        }
    }

    //The class OnSaveResult is used to handle the messages to the user about the save result
    sealed class OnSaveResult {
        data object Success : OnSaveResult()
        data class Error(val message: String) : OnSaveResult()
    }

    //Function to reset the response
    fun resetResponse() {
        _onSaveResult.value = null
    }

}

//The class SettingViewModelFactory is used to create the viewmodel
class SettingViewModelFactory() :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel() as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}