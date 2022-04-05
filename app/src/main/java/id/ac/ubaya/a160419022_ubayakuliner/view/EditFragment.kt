package id.ac.ubaya.a160419022_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ubaya.a160419022_ubayakuliner.R
import id.ac.ubaya.a160419022_ubayakuliner.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_stand_detail.*

class EditFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private val commentListAdapter = CommentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val nama = EditFragmentArgs.fromBundle(requireArguments()).nama
        val alamat = EditFragmentArgs.fromBundle(requireArguments()).alamat

        inputNama.setText(nama)
        inputAlamat.setText(alamat)
    }
}