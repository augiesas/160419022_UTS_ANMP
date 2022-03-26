package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_stand_detail.*

class StandDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    val studentList = arrayListOf<Stand>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stand_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val stand_id = StandDetailFragmentArgs.fromBundle(requireArguments()).idStand

        viewModel.fetch(stand_id)

        observeViewModel()
    }

    fun update(standDetail: ArrayList<Stand>){
        val stand = standDetail[0]
        Log.d("ajax", stand.toString())
        txtPlaceName.text = stand.nama_tempat
        txtPlaceAddress.text = stand.lokasi
        if (stand.deskripsi == null){
            txtDescription.setText("Deskripsi belum tersedia")
        }
        else{
            txtDescription.setText(stand.deskripsi)
        }
        imgDetail.loadImage(stand.url_img, progressBarDetail)
    }

    private fun observeViewModel() {
        viewModel.standLiveData.observe(viewLifecycleOwner, Observer {
            update(it)

        })
    }
}