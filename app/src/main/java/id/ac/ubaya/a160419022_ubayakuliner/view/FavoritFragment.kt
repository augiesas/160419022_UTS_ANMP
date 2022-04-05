package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_favorit.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*

class FavoritFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val standListAdapter = FavoriteListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.title = "My Favorite"

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh("favorite")

        recMyFavorite.layoutManager = LinearLayoutManager(context)
        recMyFavorite.adapter = standListAdapter

        observeViewModel()

        refreshLayoutFavorite.setOnRefreshListener {
            recMyFavorite.visibility = View.GONE
            txtErrorFavorite.visibility = View.GONE
            progressLoadFavorite.visibility = View.VISIBLE
            viewModel.refresh("favorite")
            refreshLayoutFavorite.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.standLiveData.observe(viewLifecycleOwner, Observer {
            standListAdapter.updateFavoriteList(it)
        })
        viewModel.standLoadErrorLiveData.observe(viewLifecycleOwner){
            if(it){
                txtErrorFavorite.visibility = View.VISIBLE
            }
            else{
                txtErrorFavorite.visibility = View.GONE
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if(it){
                recMyFavorite.visibility = View.GONE
                progressLoadFavorite.visibility = View.VISIBLE
            }
            else{
                recMyFavorite.visibility = View.VISIBLE
                progressLoadFavorite.visibility = View.GONE
            }
        }
    }
}