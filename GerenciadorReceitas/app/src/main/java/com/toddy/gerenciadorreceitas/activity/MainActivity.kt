package com.toddy.gerenciadorreceitas.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.gerenciadorreceitas.*
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.activity.auth.LoginActivity
import com.toddy.gerenciadorreceitas.adapter.AdapterReceitas
import com.toddy.gerenciadorreceitas.models.Receita
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView

class MainActivity : AppCompatActivity(), AdapterReceitas.OnClick {

    private var recipeList = mutableListOf<Receita>()
    private var rvReceitas: RecyclerView? = null
    private var adapterReceitas: AdapterReceitas? = null
    private var ibMore: ImageButton? = null
    private var llInfo: LinearLayout? = null
    private var tvInfo: TextView? = null
    private var ivSemReceitas: ImageView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var isSalgado: CheckBox
    private lateinit var isDoce: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponets()
        configReciclerView()
        recuperaReceita()
        listenerClicks()
        filtro()
    }

    override fun onResume() {
        super.onResume()
        recuperaReceita()
        filtro()
    }

    private fun filtro() {
        isSalgado.setOnClickListener {
            if (isDoce.isChecked) {

                isDoce.isChecked = false

            }
            progressBar!!.visibility = View.VISIBLE
            recuperaReceitaFiltrada()
        }

        isDoce.setOnClickListener {
            if (isSalgado.isChecked) {

                isSalgado.isChecked = false

            }
            progressBar!!.visibility = View.VISIBLE
            recuperaReceitaFiltrada()
        }
    }

    fun limparFiltro(view:View){
        progressBar!!.visibility = View.VISIBLE
        isSalgado.isChecked = false
        isDoce.isChecked = false
        recuperaReceita()
    }

    fun recuperaReceitaFiltrada() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("receitas_publicas")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    recipeList.clear()
                    val children = snapshot.children
                    children.forEach {
                        it.getValue(Receita::class.java).let { receita ->
                            if (isSalgado.isChecked){
                                if (receita!!.isSalgada){
                                    recipeList.add(receita!!)
                                }
                            }
                            if (isDoce.isChecked){
                                if (!receita!!.isSalgada){
                                    recipeList.add(receita!!)
                                }
                            }
                        }
                    }
                    tvInfo!!.text = ""
                } else {
                    tvInfo!!.text = "Nenhuma receita cadastrada"
                    ivSemReceitas!!.visibility = View.VISIBLE
                }
                progressBar!!.visibility = View.GONE
                recipeList.reverse()
                adapterReceitas!!.notifyDataSetChanged()
                if (recipeList.size == 0){
                    tvInfo!!.text = "Nenhuma receita cadastrada"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun recuperaReceita() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("receitas_publicas")

        llInfo!!.visibility = View.VISIBLE

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    recipeList.clear()
                    val children = snapshot.children
                    children.forEach {
                        it.getValue(Receita::class.java).let { receita ->
                            recipeList.add(receita!!)
                        }
                    }
                    tvInfo!!.text = ""
                } else {
                    tvInfo!!.text = "Nenhuma receita cadastrada"
                    ivSemReceitas!!.visibility = View.VISIBLE
                }
                progressBar!!.visibility = View.GONE
                recipeList.reverse()
                adapterReceitas!!.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initComponets() {
        ivSemReceitas = findViewById(R.id.iv_empty_recipes)
        isSalgado = findViewById(R.id.checkbox_salgada)
        isDoce = findViewById(R.id.checkbox_doce)
        rvReceitas = findViewById(R.id.rv)
        ibMore = findViewById(R.id.ib_more)
        llInfo = findViewById(R.id.ll_info)
        tvInfo = findViewById(R.id.tv_info)
        progressBar = findViewById(R.id.progress_bar)
    }


    private fun configReciclerView() {
        rvReceitas?.layoutManager = LinearLayoutManager(this)
        rvReceitas?.setHasFixedSize(true)
        adapterReceitas = AdapterReceitas(recipeList, this)
        rvReceitas!!.adapter = adapterReceitas
    }

    private fun listenerClicks() {
        ibMore?.setOnClickListener {
            val popupMenu = PopupMenu(this, ibMore)
            popupMenu.menuInflater.inflate(R.menu.menu_toolbar, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_minhas_receitas -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, MinhasReceitasActivity::class.java))
                        } else {
                            showDialog()
                        }
                    }
                    R.id.menu_sair -> {
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("Autentifica????o")
            .setCancelable(false)
            .setMessage("Voc?? n??o est?? logado, voc?? quer logar de novo?")
            .setPositiveButton("Sim") { _: DialogInterface, _: Int ->
                startActivity(Intent(this, LoginActivity::class.java))
            }.setNegativeButton("N??o") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .show()
    }

    override fun onClickListener(receita: Receita) {
        val intent = Intent(this, RecipeItemActivity::class.java)
        intent.putExtra("receita", receita)
        intent.putExtra("main", true)
        startActivity(intent)
    }
}