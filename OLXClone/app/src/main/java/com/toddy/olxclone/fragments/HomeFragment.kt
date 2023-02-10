package com.toddy.olxclone.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.CategoriasActivity
import com.toddy.olxclone.activitys.EstadosActivity
import com.toddy.olxclone.activitys.FiltrosActivity
import com.toddy.olxclone.activitys.FormActivity
import com.toddy.olxclone.adapter.AnuncioAdapter
import com.toddy.olxclone.auth.LoginActivity
import com.toddy.olxclone.helper.SPFiltro
import com.toddy.olxclone.model.Anuncio
import com.toddy.olxclone.model.Filtro

class HomeFragment : Fragment(), AnuncioAdapter.OnClickListener {
    private lateinit var anunciosAdapter: AnuncioAdapter
    private var anunciosLst = mutableListOf<Anuncio>()

    private lateinit var progressBar: ProgressBar
    private lateinit var tvInfo: TextView
    private lateinit var btnNovoAnuncio: Button
    private lateinit var rvAnuncios: RecyclerView

    private lateinit var btnRegiao: Button
    private lateinit var btnCategoria: Button
    private lateinit var btnFiltro: Button

    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private lateinit var editSearchView: EditText

    private var filtro = Filtro()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        initComponents(view)
        confiRv()
        configClicks(view)
        configSearchView()
        return view
    }

    private fun configSearchView() {
        editSearchView = searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        searchView.findViewById<androidx.appcompat.widget.AppCompatImageView>(androidx.appcompat.R.id.search_close_btn)
            .setOnClickListener {
                limparPesquisa()
            }

        searchView.setOnQueryTextFocusChangeListener(object : SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                SPFiltro.setFiltro(requireActivity(), "pesquisa", p0!!)
                configFiltros()
                ocultarTeclado()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onFocusChange(p0: View?, p1: Boolean) {

            }
        })

    }

    private fun limparPesquisa() {
        searchView.clearFocus()
        editSearchView.setText("")
        SPFiltro.setFiltro(requireActivity(), "pesquisa", "")
        configFiltros()
        ocultarTeclado()
    }

    private fun ocultarTeclado() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun configFiltros() {
        filtro = SPFiltro.getFiltro(requireActivity())

        if (filtro.estado!!.regiao.isNotEmpty()) {
            btnRegiao.text = filtro.estado!!.regiao
        } else {
            btnRegiao.text = "Regiões"
        }

        if (filtro.categoria.isNotEmpty()) {
            btnRegiao.text = filtro.categoria
        } else {
            btnRegiao.text = "Categorias"
        }

        recuperaAnuncios()
    }

    private fun configClicks(view: View) {
        btnNovoAnuncio.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                startActivity(Intent(activity, FormActivity::class.java))
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        btnCategoria.setOnClickListener {
            val intent = Intent(requireActivity(), CategoriasActivity::class.java)
            intent.putExtra("todas", true)
            startActivity(intent)
        }

        btnRegiao.setOnClickListener {
            startActivity(Intent(activity, EstadosActivity::class.java))
        }

        btnFiltro.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    FiltrosActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        recuperaAnuncios()
    }

    private fun initComponents(view: View) {
        progressBar = view.findViewById(R.id.progress_bar)
        tvInfo = view.findViewById(R.id.tv_info)
        rvAnuncios = view.findViewById(R.id.rv_anuncios)
        btnNovoAnuncio = view.findViewById(R.id.btn_novo_anuncio)
        btnRegiao = view.findViewById(R.id.btn_regiao)
        btnCategoria = view.findViewById(R.id.btn_categorias)
        btnFiltro = view.findViewById(R.id.btn_filtro)
        searchView = view.findViewById(R.id.search_view)
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
        Toast.makeText(requireContext(), anuncio.titulo, Toast.LENGTH_SHORT).show()
    }
}