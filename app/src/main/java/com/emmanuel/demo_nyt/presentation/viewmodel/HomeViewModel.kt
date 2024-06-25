package com.emmanuel.demo_nyt.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.emmanuel.demo_nyt.R
import com.emmanuel.demo_nyt.domain.model.Article
import com.emmanuel.demo_nyt.domain.repository.SettingsRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

/**
 * Home View Model.
 *
 * @param application is the application context.
 * @return a view model that contains the data retrieved from the API and the messages to the user about the request result.
 */
class HomeViewModel(private val application: Application) : ViewModel() {

    private var _articles: MutableLiveData<List<Article?>> = MutableLiveData()
    val articles: LiveData<List<Article?>?>
        get() = _articles

    private val _onRequestResult = MutableLiveData<OnRequestResult?>(null)
    val onRequestResult: LiveData<OnRequestResult?> get() = _onRequestResult

    val tag = "HOME_VIEW_MODEL"

    private fun requestArticles() {
        if (isInternetAvailable()) {
            viewModelScope.launch(context = Dispatchers.Main) {

                val settingsRepository = SettingsRepository()
                val apiKey = settingsRepository.getSettingByName("api_key")?.value
                    ?: application.getString(R.string.api_key)

                if (apiKey.isNotEmpty()) {
                    val url =
                        application.getString(R.string.articles_url) + apiKey
                    val queue = Volley.newRequestQueue(application.applicationContext)
                    val request: JsonObjectRequest = @SuppressLint("NullSafeMutableLiveData")
                    object : JsonObjectRequest(
                        Method.GET,
                        url,
                        null,
                        { response ->

                            try {
                                Log.d(tag, response.toString())
                                val articles: JSONArray = response.getJSONArray("results")
                                val list: ArrayList<Article?> = ArrayList()

                                for (i in 0 until articles.length()) {
                                    val item = articles.getJSONObject(i)
                                    Log.d(tag, "Article added: $item")
                                    val article = Gson().fromJson<Article>(
                                        item.toString(),
                                        Article::class.java
                                    )
                                    list.add(article)
                                }
                                _articles.postValue(list)
                                Log.d(tag, "Article list size " + list.size)
                                if (list.isEmpty()) {
                                    _onRequestResult.value =
                                        OnRequestResult.Error(application.getString(R.string.empty_articles))
                                }
                            } catch (e: Exception) {
                                Log.d(tag, e.toString())
                                _onRequestResult.value =
                                    OnRequestResult.Error(application.getString(R.string.error_parsing_data))

                            }

                        }, { error ->
                            error.printStackTrace()
                            Log.d(tag, error.toString())
                            _onRequestResult.value =
                                OnRequestResult.Error(application.getString(R.string.error_parsing_data))

                        }) {

                    }

                    request.retryPolicy = DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )

                    request.setShouldCache(false)
                    queue.add(request)
                } else {
                    _onRequestResult.value =
                        OnRequestResult.Error(
                            application.getString(R.string.error_api_key)
                        )
                }

            }
        } else {
            _onRequestResult.value =
                OnRequestResult.Error(
                    application.getString(R.string.error_network)
                )
        }

    }

    //Function for check if the device is connected to the internet
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    //The class OnRequestResult is used to handle the messages to the user about the request result
    sealed class OnRequestResult {
        data object Success : OnRequestResult()
        data class Error(val message: String) : OnRequestResult()
    }

    //Function to reset the response
    fun resetResponse() {
        _onRequestResult.value = null
    }

    // As soon as the viewmodel is initialized make a request to the api
    init {
        requestArticles()
    }

}

//The class HomeViewmodelFactory is used to create the viewmodel
class HomeViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}