package com.toddy.olxclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.CategoriasActivity
import com.toddy.olxclone.activitys.FormActivity
import com.toddy.olxclone.adapter.AnuncioAdapter
import com.toddy.olxclone.auth.LoginActivity
import com.toddy.olxclone.model.Anuncio
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView

class HomeFragment : Fragment(), AnuncioAdapter.OnClickListener {
    private lateinit var anunciosAdapter: AnuncioAdapter
    private var anunciosLst = mutableListOf<Anuncio>()

    private lateinit var progressBar: ProgressBar
    private lateinit var tvInfo: TextView
    private lateinit var btnNovoAnuncio:Button
    private lateinit var rvAnuncios: RecyclerView
    private lateinit var btnRegiao:Button
    private lateinit var btnCategoria:Button
    private lateinit var btnFiltro:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View =  inflater.inflate(R.layout.fragment_home, container, false)

        initComponents(view)
        confiRv()
        configClicks(view)

        return view;
    }

    private fun configClicks(view:View){
        btnNovoAnuncio.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null){
                startActivity(Intent(activity, FormActivity::class.java))
            }else{
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        btnCategoria.setOnClickListener {
            val intent = Intent(requireActivity(), CategoriasActivity::class.java)
            intent.putExtra("todas", true)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        recuperaAnuncios()
    }

    private fun initComponents(view:View){
        progressBar = view.findViewById(R.id.progress_bar)
        tvInfo = view.findViewById(R.id.tv_info)
        rvAnuncios = view.findViewById(R.id.rv_anuncios)
        btnNovoAnuncio = view.findViewById(R.id.btn_novo_anuncio)
        btnRegiao = view.findViewById(R.id.btn_regiao)
        btnCategoria = view.findViewById(R.id.btn_categorias)
        btnFiltro = view.findViewById(R.id.btn_filtro)
    }

    private fun confiRv() {
        rvAnuncios.layoutManager = LinearLayoutManager(activity)
        rvAnuncios.setHasFixedSize(true)
        anunciosAdapter = AnuncioAdapter(anunciosLst, this)
        rvAnuncios.adapter = anunciosAdapter
    }

    private fun recuperaAnuncios() {
            val anuncioRef: DatabaseReference =
                FirebaseDatabase.getInstance().reference.child("anuncios_publicos")


            anuncioRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        anunciosLst.clear()
                        tvInfo.text = ""
                        val children = snapshot.children
                        children.forEach {
                            it.getValue(Anuncio::class.java).let { anuncio ->
                                anunciosLst.add(anuncio!!)
                            }
                        }
                        anunciosLst.reverse()
                        anunciosAdapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    } else {
                        tvInfo.text = "Nenhum anúncio cadastrado"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun OnClick(anuncio: Anuncio) {
       Toast.makeText(requireContext(),anuncio.titulo,Toast.LENGTH_SHORT).show()
    }
}