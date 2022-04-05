package id.ac.ubaya.a160419022_ubayakuliner.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponse
import id.ac.ubaya.a160419022_ubayakuliner.model.ApiResponseComment
import id.ac.ubaya.a160419022_ubayakuliner.model.Comment
import id.ac.ubaya.a160419022_ubayakuliner.model.Stand
import id.ac.ubaya.a160419022_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.comment_list_item.view.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class CommentListAdapter (val commentList:ArrayList<Comment>):RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>(){
    class CommentViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.comment_list_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val api:ArrayList<ApiResponseComment> = arrayListOf(ApiResponseComment(commentList))

        val comment = api[0].data[position]
        with(holder.view){
            txtUsername.text = comment.nama_pengguna
            txtCommentDate.text = comment.date
            txtComment.text = comment.comment
        }
    }

    override fun getItemCount() = commentList.size

    fun updateCommentList(newCommentList: ApiResponseComment){
        commentList.clear()
        commentList.addAll(newCommentList.data)
        notifyDataSetChanged()
    }
}
