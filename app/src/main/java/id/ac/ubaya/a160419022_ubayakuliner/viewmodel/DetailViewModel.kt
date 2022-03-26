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
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand

class DetailViewModel (application: Application) : AndroidViewModel(application){

    val standLiveData = MutableLiveData<ArrayList<Stand>>()
    val standLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue?=null

    fun fetch(standId: Int){

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubaya.fun/native/160419022/ANMP/getdetailstand.php?id=$standId"
        Log.d("masuk","masuk not null")

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
//                val sType = object : TypeToken<ArrayList<Stand>>(){}.type
                val result = Gson().fromJson<ArrayList<Stand>>(it, Stand::class.java)
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