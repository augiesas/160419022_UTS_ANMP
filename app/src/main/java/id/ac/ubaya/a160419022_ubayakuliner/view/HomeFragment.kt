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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponse
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseGenre
import id.ac.ubaya.a160419022_ubayakuliner.model.Genre
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListGenreModel
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_user.*


class HomeFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private lateinit var viewModelType: ListGenreModel
    private val standListAdapter = HomeListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.title = "Ubaya Kuliner"

        viewModel =ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh("favorite")

        recFavorite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recFavorite.adapter = standListAdapter

        viewModelType = ViewModelProvider(this).get(ListGenreModel::class.java)
        viewModelType.getGenre()

        observeViewModel()

        refreshLayoutHome.setOnRefreshListener {
            recFavorite.visibility = View.GONE
            txtErrorHome.visibility = View.GONE
            progressLoadHome.visibility = View.VISIBLE
            viewModel.refresh("favorite")
            viewModelType.getGenre()
            refreshLayoutHome.isRefreshing = false
        }

        btnTrending.setOnClickListener {
            val action = HomeFragmentDirections.actionTrending()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.standLiveData.observe(viewLifecycleOwner, Observer {
            standListAdapter.updateStandList(it)
        })
        viewModelType.genreLiveData.observe(viewLifecycleOwner, Observer {
            Tipe(it)
        })
        viewModel.standLoadErrorLiveData.observe(viewLifecycleOwner){
            if(it){
                txtErrorHome.visibility = View.VISIBLE
            }
            else{
                txtErrorHome.visibility = View.GONE
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if(it){
                recFavorite.visibility = View.GONE
                progressLoadHome.visibility = View.VISIBLE
            }
            else{
                recFavorite.visibility = View.VISIBLE
                progressLoadHome.visibility = View.GONE
            }
        }
    }

    private fun addChip(id:Int?, text:String?)
    {
        val chip =Chip(context)
        chip.text = text
        if (id != null) {
            chip.id = id
        }
        chipTipeMenu.addView(chip)
    }

    private fun removeChip()
    {
        chipTipeMenu.removeAllViews()
    }

    fun Tipe(tipe: ApiResponseGenre){
        removeChip()
        for(i in tipe.data.indices)
        {
            val result = tipe.data[i]
            Log.d("ajax", result.toString())
            addChip(result.id, result.name)
        }
    }
}