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
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseGenre
import id.ac.ubaya.a160419022_ubayakuliner.model.Genre
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand

class ListGenreModel (application: Application) : AndroidViewModel(application) {
    val genreLiveData = MutableLiveData<ApiResponseGenre>()
    val genreLoadErrorLiveData = MutableLiveData<Boolean>()
    val genreLoadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue?=null

    fun getGenre(){
        genreLoadErrorLiveData.value = false

            queue = Volley.newRequestQueue(getApplication())
            val url = "https://ubaya.fun/native/160419022/ANMP/getalltype.php"

            Log.d("masuk","masuk not null")

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                {
//                    val sType = object : TypeToken<ArrayList<Genre>>(){}.type
//                    val result = Gson().fromJson<ArrayList<Genre>>(it, sType)
                    val result: ApiResponseGenre = Gson().fromJson(it, ApiResponseGenre::class.java)

                    genreLiveData.value = result

                    genreLoadingLiveData.value = false
                    Log.d("showvolley",it)
                },
                {
                    genreLoadErrorLiveData.value = true
                    genreLoadingLiveData.value = false
                    Log.d("errorvolley",it.toString())

                }
            ).apply {
                tag = "TAG"
            }
            queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

}