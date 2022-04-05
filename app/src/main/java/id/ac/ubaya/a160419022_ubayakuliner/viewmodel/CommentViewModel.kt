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
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseComment
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseGenre
import id.ac.ubaya.a160419022_ubayakuliner.model.Comment

class CommentViewModel (application: Application) : AndroidViewModel(application){

    val commentsLiveData = MutableLiveData<ApiResponseComment>()
    val commentsLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue?=null

    fun fetch(standId: Int){
        commentsLoadErrorLiveData.value = false


        queue = Volley.newRequestQueue(getApplication())
        val url = "https://ubaya.fun/native/160419022/ANMP/getcomment.php?id=$standId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
//                val sType = object : TypeToken<ArrayList<Comment>>(){}.type
//                val result = Gson().fromJson<ArrayList<Comment>>(it, sType)
                val result: ApiResponseComment = Gson().fromJson(it, ApiResponseComment::class.java)

                commentsLiveData.value = result

                loadingLiveData.value = false
                Log.d("showvolley",it)
            },
            {
                commentsLoadErrorLiveData.value = true
                loadingLiveData.value = false
                Log.d("errorvolley",it.toString())

            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }
}