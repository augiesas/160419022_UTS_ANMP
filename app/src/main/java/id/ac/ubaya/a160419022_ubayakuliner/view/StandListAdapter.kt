package id.ac.ubaya.a160419022_ubayakuliner.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.place_list_item.view.*

class StandListAdapter(val standList:ArrayList<Stand>):RecyclerView.Adapter<StandListAdapter.StandViewHolder>(){
    class StandViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.place_list_item, parent, false)
        return StandViewHolder(view)
    }

    override fun onBindViewHolder(holder: StandViewHolder, position: Int) {
        val stand = standList[position]

        with(holder.view){
            txtPlace.text =stand.nama_tempat
            imgPlace.loadImage(stand.url_img, progressListImg)
            cardPlace.setOnClickListener {
                val id = stand.id
                Log.d("test",id.toString())
                val action = stand.id?.let { it1 -> SearchFragmentDirections.actionStandDetail(it1) }
                if (action != null) {
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = standList.size

    fun updateStandList(newStudentList: ArrayList<Stand>){
        standList.clear()
        standList.addAll(newStudentList)
        notifyDataSetChanged()
    }
}

