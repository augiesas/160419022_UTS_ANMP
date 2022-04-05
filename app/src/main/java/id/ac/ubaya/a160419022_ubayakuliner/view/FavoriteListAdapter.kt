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
import kotlinx.android.synthetic.main.myfavorite_list_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class FavoriteListAdapter (val favoriteList:ArrayList<Stand>): RecyclerView.Adapter<FavoriteListAdapter.ListViewHolder>(){
    class ListViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.myfavorite_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val api:ArrayList<ApiResponse> = arrayListOf(ApiResponse(favoriteList))

        val stand = api[0].data[position]

        with(holder.view){
            txtNama.text = stand.nama_tempat
            imgTempat.loadImage(stand.url_img, progressLoadImgFavorite)
            if(stand.deskripsi == null)
            {
                txtDesc.text = ""
            } else {
                txtDesc.text = stand.deskripsi
            }
            btnDetail.setOnClickListener {
                val id = stand.id
                Log.d("test",id.toString())
                val action = FavoritFragmentDirections.actionStandDetailFromFavorite(stand.id!!,
                    stand.nama_tempat.toString()
                )
                if (action != null) {
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = favoriteList.size

    fun updateFavoriteList(newFavoriteList: ApiResponse){
        favoriteList.clear()
        favoriteList.addAll(newFavoriteList.data)
        notifyDataSetChanged()
    }
}
