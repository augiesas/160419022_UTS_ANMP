package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseUser
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.fetch()

        observeViewModel()

        btnEditProfile.setOnClickListener{
            val action = UserFragmentDirections.actionEdit(txtNamaUser.text.toString(), txtAlamat.text.toString())
            Navigation.findNavController(it).navigate(action)
        }

        btnMyFavorite.setOnClickListener{
            val action = UserFragmentDirections.actionFavorite()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun update(userDetail: ApiResponseUser){
        val user = userDetail.data[0]
        txtNamaUser.text = user.username
        txtAlamat.text = user.alamat
        Log.d("check_user",user.url_image.toString())
        imgPengguna.loadImage(user.url_image, progressLoadUser)
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            update(it)
        })
    }
}