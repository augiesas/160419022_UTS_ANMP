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
import kotlinx.android.synthetic.main.button_more_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.cardPlace


class HomeListAdapter(val standList:ArrayList<Stand>): RecyclerView.Adapter<HomeListAdapter.StandViewHolder>(){
    class StandViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    val limit:Int = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view:View

        if(viewType == R.layout.place_list_item){
            view = inflater.inflate(R.layout.place_list_item, parent, false)

        } else {
            view = inflater.inflate(R.layout.button_more_item, parent, false)
        }
        return StandViewHolder(view)
    }

    override fun onBindViewHolder(holder: StandViewHolder, position: Int) {
        if(position == limit-1) {
            holder.view.button.setOnClickListener {
                val action = HomeFragmentDirections.actionTrending()
                Navigation.findNavController(it).navigate(action)
            }
        } else {
            val api:ArrayList<ApiResponse> = arrayListOf(ApiResponse(standList))

            val stand = api[0].data[position]

            with(holder.view){
                txtPlace.text =stand.nama_tempat
//            Log.d("test",stand.data[0].nama_tempat.toString())

                imgPlace.loadImage(stand.url_img, progressListImg)
                cardPlace.setOnClickListener {
                    val id = stand.id
                    val action = HomeFragmentDirections.actionStandDetailFromHome(stand.id!!,
                        stand.nama_tempat.toString()
                    )
                    if (action != null) {
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return Math.min(standList.size, limit)
    }

//    override fun getItemCount() = standList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == limit-1) R.layout.button_more_item else R.layout.place_list_item
    }

    fun updateStandList(newStandList: ApiResponse){
        standList.clear()
        standList.addAll(newStandList.data)
        notifyDataSetChanged()
    }
}