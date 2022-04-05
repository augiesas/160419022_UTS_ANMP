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
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseUser
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.model.User

class UserViewModel  (application: Application) : AndroidViewModel(application) {

    val userLiveData = MutableLiveData<ApiResponseUser>()
    val userLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch() {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubaya.fun/native/160419022/ANMP/getuser.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
//                val sType = object : TypeToken<ArrayList<User>>(){}.type
//                val result = Gson().fromJson<ArrayList<User>>(it, sType)
                val result: ApiResponseUser = Gson().fromJson(it, ApiResponseUser::class.java)

                userLiveData.value = result

                loadingLiveData.value = false
                Log.d("showvolley", it)
            },
            {
                userLoadErrorLiveData.value = true
                loadingLiveData.value = false
                Log.d("errorvolley", it.toString())

            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }
}