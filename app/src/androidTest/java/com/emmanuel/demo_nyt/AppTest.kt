package com.emmanuel.demo_nyt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import com.emmanuel.demo_nyt.domain.repository.SettingsRepository
import com.emmanuel.demo_nyt.presentation.viewmodel.SettingViewModel
import androidx.lifecycle.Observer

import com.emmanuel.demo_nyt.domain.model.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue

/* The class AppTest is used to test the common methods of the app */
@ExperimentalCoroutinesApi
class AppTest {

    /* This rule is used to swap the background executor used by the Architecture Components with a
    different one which executes each task synchronously */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // This dispatcher will be used for testing coroutines
    private val dispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    // Reset the main dispatcher after the test runs
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    private lateinit var settingViewModel: SettingViewModel
    // Fake repository for testing
    private lateinit var settingsRepository: FakeSettingsRepository

    @Before
    fun initViewModel() {
        settingsRepository = FakeSettingsRepository()
        settingViewModel = SettingViewModel()
    }

    // The test for saveApiKey method
    @Test
    fun testSaveApiKeySuccess() {
        val apiKey = "123456789"
        val observer = Observer<SettingViewModel.OnSaveResult?> { result ->
            result?.let {
                // Assert inside onChanged callback
                assertTrue(result is SettingViewModel.OnSaveResult.Success)
            }
        }

        // Observe onSaveResult LiveData
        settingViewModel.onSaveResult.observeForever(observer)

        try {
            // Call saveApiKey method
            settingViewModel.saveApiKey(apiKey)

            // Advance the coroutine dispatcher to execute tasks
            dispatcher.scheduler.advanceUntilIdle()

        } finally {
            // Clean up by removing the observer
            settingViewModel.onSaveResult.removeObserver(observer)
        }
    }

    // The test for saveApiKey method with error
    @Test
    fun testSaveApiKeyError() {
        //The api key is empty so it should return an error
        val apiKey = ""
        val observer = Observer<SettingViewModel.OnSaveResult?> { result ->
            result?.let {
                // Assert inside onChanged callback
                assertTrue(result is SettingViewModel.OnSaveResult.Error)
            }
        }

        // Observe onSaveResult LiveData
        settingViewModel.onSaveResult.observeForever(observer)

        try {
            // Call saveApiKey method
            settingViewModel.saveApiKey(apiKey)

            // Advance the coroutine dispatcher to execute tasks
            dispatcher.scheduler.advanceUntilIdle()

        } finally {
            // Clean up by removing the observer
            settingViewModel.onSaveResult.removeObserver(observer)
        }
    }

    // The test for resetResponse method after the request
    @Test
    fun testResetResponse() {
        // Set a non-null value to onSaveResult
        settingViewModel.saveApiKey("testApiKey")

        // Reset response
        settingViewModel.resetResponse()

        // Assert that onSaveResult is null
        assertNull(settingViewModel.onSaveResult.value)
    }

    // The test for getSettingByName method
    @Test
    fun testGetSettingByName() = runBlocking {
        settingsRepository.setSetting(Setting("api_key", "testValue"))
        val result = settingsRepository.getSettingByName("api_key")
        assertEquals("testValue", result?.value)
    }

    // FakeSettingsRepository to mock or fake the behavior of SettingsRepository
    class FakeSettingsRepository : SettingsRepository() {

        private var shouldInsertSucceed = true
        private var settingsMap: MutableMap<String, Setting> = mutableMapOf()

        fun setSetting(setting: Setting) {
            settingsMap[setting.name] = setting
        }

        override suspend fun getSettingByName(settingName: String): Setting? {
            return settingsMap[settingName]
        }

        override suspend fun insertSetting(setting: Setting): Boolean {
            // Simulate insertion success or failure based on shouldInsertSucceed
            return shouldInsertSucceed
        }
    }

}