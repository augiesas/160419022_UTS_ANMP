package id.ac.ubaya.a160419022_ubayakuliner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponse


class ListViewModel(application: Application) : AndroidViewModel(application) {
    val standLiveData = MutableLiveData<ApiResponse>()
    val favoriteLiveData = MutableLiveData<ApiResponse>()
    val standLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue?=null

    fun refresh(search:String = "all"){
        standLoadErrorLiveData.value = false

        if (search == "all"){
            queue = Volley.newRequestQueue(getApplication())
            val url = "https://ubaya.fun/native/160419022/ANMP/getalldata.php"

            Log.d("masuk","masuk not null")

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
//                    val sType = object : TypeToken<ApiResponse>(){}.type
//                    val result = Gson().fromJson<ArrayList<ApiResponse>>(it, sType)
                    val result: ApiResponse = Gson().fromJson(it, ApiResponse::class.java)
                    standLiveData.value = result

                    loadingLiveData.value = false
                    Log.d("showvolley",it)
                },
                {
                    standLoadErrorLiveData.value = true
                    loadingLiveData.value = false
                    Log.d("errorvolley",it.toString())

                }
            ).apply {
                tag = "TAG"
            }
            queue?.add(stringRequest)
        }
        else if (search == "favorite"){
            Log.d("masuk","masuk favorite")

            queue = Volley.newRequestQueue(getApplication())
            val url = "https://ubaya.fun/native/160419022/ANMP/getfavorite.php"
            Log.d("masuk",url)

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
//                    val sType = object : TypeToken<ArrayList<Stand>>(){}.type
//                    val result = Gson().fromJson<ArrayList<Stand>>(it, sType)
                    val result: ApiResponse = Gson().fromJson(it, ApiResponse::class.java)

                    standLiveData.value = result

                    loadingLiveData.value = false
                    Log.d("showvolley",it)
                },
                {
                    standLoadErrorLiveData.value = true
                    loadingLiveData.value = false
                    Log.d("errorvolley",it.toString())

                }
            ).apply {
                tag = "TAG"
            }
            queue?.add(stringRequest)
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}