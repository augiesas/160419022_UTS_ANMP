package id.ac.ubaya.a160419022_ubayakuliner.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponse
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.place_list_item.view.*

class TrendingListAdapter(val standList:ArrayList<Stand>): RecyclerView.Adapter<TrendingListAdapter.TrendingViewHolder>(){
    class TrendingViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.place_list_item, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val api:ArrayList<ApiResponse> = arrayListOf(ApiResponse(standList))

        val stand = api[0].data[position]
        Log.d("test",itemCount.toString())

        with(holder.view){
            txtPlace.text =stand.nama_tempat
//            Log.d("test",stand.data[0].nama_tempat.toString())

            imgPlace.loadImage(stand.url_img, progressListImg)
            cardPlace.setOnClickListener {
                val id = stand.id
                Log.d("test",id.toString())
                val action = TrendingFragmentDirections.actionDetailFromTrending(stand.id!!,
                    stand.nama_tempat.toString()
                )
                if (action != null) {
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = standList.size

    fun updateStandList(newStandList: ApiResponse){
        standList.clear()
        standList.addAll(newStandList.data)
        notifyDataSetChanged()
    }
}