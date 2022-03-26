package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val standListAdapter = StandListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var search = txtInputSearch.editText
        viewModel =ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh(search.toString())

        recViewSearch.layoutManager = GridLayoutManager(context,3)
        recViewSearch.adapter = standListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recViewSearch.visibility = View.GONE
            txtError.visibility = View.GONE
            progressLoad.visibility = View.VISIBLE
            viewModel.refresh(search.toString())
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.standLiveData.observe(viewLifecycleOwner, Observer {
            standListAdapter.updateStandList(it)
        })
        viewModel.standLoadErrorLiveData.observe(viewLifecycleOwner){
            if(it){
                txtError.visibility = View.VISIBLE
            }
            else{
                txtError.visibility = View.GONE
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if(it){
                recViewSearch.visibility = View.GONE
                progressLoad.visibility = View.VISIBLE
            }
            else{
                recViewSearch.visibility = View.VISIBLE
                progressLoad.visibility = View.GONE
            }
        }
    }
}