package com.toddy.olxclone.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.olxclone.R
import com.toddy.olxclone.adapter.AnuncioAdapter
import com.toddy.olxclone.model.Anuncio
import com.tsuryo.swipeablerv.SwipeableRecyclerView
import java.util.EventListener


class MeusAnunciosFragment : Fragment(), AnuncioAdapter.OnClickListener {
    private lateinit var progressBar: ProgressBar
    private lateinit var tvInfo: TextView
    private lateinit var btnLogar: Button
    private lateinit var rvAnuncios: SwipeableRecyclerView

    private lateinit var anunciosAdapter: AnuncioAdapter
    private var anunciosLst = mutableListOf<Anuncio>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_meus_anuncios, container, false)

        initComponents(view)
        confiRv()
        return view
    }

    override fun onStart() {
        super.onStart()
        recuperaAnuncios()
    }

    private fun confiRv() {
        rvAnuncios.layoutManager = LinearLayoutManager(activity)
        rvAnuncios.setHasFixedSize(true)
        anunciosAdapter = AnuncioAdapter(anunciosLst, this)
        rvAnuncios.adapter = anunciosAdapter
    }

    private fun initComponents(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        tvInfo = view.findViewById(R.id.tv_info)
        btnLogar = view.findViewById(R.id.btn_logar)
        rvAnuncios = view.findViewById(R.id.rv_anuncios)
    }

    private fun recuperaAnuncios() {
        if (FirebaseAuth.getInstance().currentUser != null){
            val anuncioRef: DatabaseReference =
                FirebaseDatabase.getInstance().reference.child("meus_anuncios").child(FirebaseAuth.getInstance().currentUser!!.uid)

            anuncioRef.addListenerForSingleValueEvent(object :  ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        tvInfo.text = ""
                        val children =snapshot.children
                        children.forEach {
                            it.getValue(Anuncio::class.java).let {
                                anuncio->anunciosLst.add(anuncio!!)
                            }
                        }
                        anunciosLst.reverse()
                        anunciosAdapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }else{
                        tvInfo.text = "Nenhum anúncio cadastrado"
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }else{
            btnLogar.visibility = View.VISIBLE
            tvInfo.text = ""
            progressBar.visibility = View.GONE
        }
    }

    override fun OnClick(anuncio: Anuncio) {
        TODO("Not yet implemented")
    }
}