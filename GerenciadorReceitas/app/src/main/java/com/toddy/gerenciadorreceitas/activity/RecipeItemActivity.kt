package com.toddy.gerenciadorreceitas.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita
import com.toddy.gerenciadorreceitas.models.Usuario

class RecipeItemActivity : AppCompatActivity() {
    private lateinit var fotoReceita: ImageView
    private var tvReceitaNome: TextView? = null
    private var tvReceitaDescricao: TextView? = null
    private var tvAutor: TextView? = null
    private var ll_ingredientes: LinearLayout? = null
    private var receita = Receita()
    private var usuario = Usuario()
    private var ingredientList: MutableList<String> = ArrayList()
    private var tvTxtToolbar: TextView? = null
    private var ibVoltar: ImageButton? = null
    private var btnUpdate: Button? = null
    private var isMain: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_item)

        val bundle = intent.extras
        receita = bundle?.getSerializable("receita") as Receita
        isMain = intent.getBooleanExtra("main", false)

        initComponets()
        recuperaAutor()
        carregarReceita()
        createTVIngredientes()
        onClickListener()
    }

    private fun initComponets() {
        tvAutor = findViewById(R.id.tv_receita_autor)
        tvReceitaNome = findViewById(R.id.tv_receita_nome_item)
        tvReceitaDescricao = findViewById(R.id.tv_Receita_descricao_item)
        ll_ingredientes = findViewById(R.id.ll_ingredientes)
        tvTxtToolbar = findViewById(R.id.toolbar_txt)
        ibVoltar = findViewById(R.id.iv_back)
        fotoReceita = findViewById(R.id.foto_receita)
        btnUpdate = findViewById(R.id.btn_update)
    }

    fun createTVIngredientes() {
        ingredientList = receita!!.ingredientes.split(",") as MutableList<String>

        for (txt in ingredientList) {
            var textView = TextView(this)
            textView.text = " - ${txt.trim()}"
            textView.setTextColor(Color.parseColor("#363537"))
            textView.textSize = 18f
            ll_ingredientes!!.addView(textView)
        }

    }

    fun onClickListener() {
        ibVoltar?.setOnClickListener {
            finish()
        }

        if (isMain) {
            btnUpdate!!.visibility = View.GONE
        } else {
            btnUpdate?.setOnClickListener {
                val intent = Intent(this, FormActivity::class.java)
                intent.putExtra("receita", receita)
                startActivity(intent)
            }
        }

    }

    private fun carregarReceita() {

        tvReceitaNome!!.text = receita!!.receita
        tvReceitaDescricao!!.text = receita!!.descricao
        Picasso.get().load(receita!!.urlImagem).into(fotoReceita)
        tvTxtToolbar!!.text = receita!!.receita


    }

    private fun recuperaAutor() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("usuarios").child(receita.idUser)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usuario = snapshot.getValue(Usuario::class.java)!!
                tvAutor!!.text = "Por: ${usuario.nome}"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}