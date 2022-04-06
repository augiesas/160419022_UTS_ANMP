package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponse
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseGenre
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.CommentViewModel
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.DetailViewModel
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListGenreModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_stand_detail.*

class StandDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModel2: CommentViewModel
    private lateinit var viewModelType: ListGenreModel
    private val commentListAdapter = CommentListAdapter(arrayListOf())
    private var judul:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stand_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel2 = ViewModelProvider(this).get(CommentViewModel::class.java)
        viewModelType = ViewModelProvider(this).get(ListGenreModel::class.java)

        val stand_id = StandDetailFragmentArgs.fromBundle(requireArguments()).idStand
        judul = StandDetailFragmentArgs.fromBundle(requireArguments()).namaStand
        (activity as AppCompatActivity).supportActionBar?.title = "${judul}"

        Log.d("stand_id", stand_id.toString())

        viewModel.fetch(stand_id)
        viewModel2.fetch(stand_id)
        viewModelType.getGenre("menu",stand_id)

        recComment.layoutManager = LinearLayoutManager(context)
        recComment.adapter = commentListAdapter

        observeViewModel()

        btnAddReview.setOnClickListener {
            val action = StandDetailFragmentDirections.actionAddReview()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun addChip(id:Int?, text:String?)
    {
        val chip = Chip(context)
        chip.text = text
        if (id != null) {
            chip.id = id
        }
        groupMenuType.addView(chip)
    }

    private fun removeChip()
    {
        groupMenuType.removeAllViews()
    }

    fun Tipe(tipe: ApiResponseGenre){
        removeChip()
        for(i in tipe.data.indices)
        {
            val result = tipe.data[i]
            Log.d("ajax2", result.toString())
            addChip(result.id, result.name)
        }
    }

    fun update(standDetail: ApiResponse){
//        val api:ArrayList<ApiResponse> = arrayListOf(ApiResponse(standDetail))
        Log.d("ajax1", standDetail.toString())

        val stand = standDetail.data[0]
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
        viewModelType.genreLiveData.observe(viewLifecycleOwner, Observer {
            Tipe(it)
        })
        viewModel2.commentsLiveData.observe(viewLifecycleOwner, Observer {
            commentListAdapter.updateCommentList(it)
        })
    }
}