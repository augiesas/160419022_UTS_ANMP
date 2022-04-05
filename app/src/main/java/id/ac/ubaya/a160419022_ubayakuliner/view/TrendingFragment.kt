package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.refreshLayoutTrending
import kotlinx.android.synthetic.main.fragment_trending.*

class TrendingFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val standListAdapter = TrendingListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh("favorite")
//        viewModel.Like()

        recTrending.layoutManager = GridLayoutManager(context,2)
        recTrending.adapter = standListAdapter

        observeViewModel()

        refreshLayoutTrending.setOnRefreshListener {
            recTrending.visibility = View.GONE
            txtErrorTrending.visibility = View.GONE
            progressLoadTrending.visibility = View.VISIBLE
            viewModel.refresh("favorite")
            refreshLayoutTrending.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.standLiveData.observe(viewLifecycleOwner, Observer {
            standListAdapter.updateStandList(it)
        })
        viewModel.standLoadErrorLiveData.observe(viewLifecycleOwner){
            if(it){
                txtErrorTrending.visibility = View.VISIBLE
            }
            else{
                txtErrorTrending.visibility = View.GONE
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if(it){
                recTrending.visibility = View.GONE
                progressLoadTrending.visibility = View.VISIBLE
            }
            else{
                recTrending.visibility = View.VISIBLE
                progressLoadTrending.visibility = View.GONE
            }
        }
    }
}