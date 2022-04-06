package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseUser
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_review.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user.*

class AddReviewFragment : BottomSheetDialogFragment() {
    private lateinit var viewModelUser: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModelUser.fetch()

        observeViewModel()
    }

    fun update(userDetail: ApiResponseUser){
        val user = userDetail.data[0]
        txtNamaReviewer.text = user.username
        imgreviewer.loadImage(user.url_image, progressLoadReviewer)
    }

    private fun observeViewModel() {
        viewModelUser.userLiveData.observe(viewLifecycleOwner, Observer {
            update(it)
        })
    }
}