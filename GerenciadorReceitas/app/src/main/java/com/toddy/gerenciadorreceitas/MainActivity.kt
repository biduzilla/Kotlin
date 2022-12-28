package com.toddy.gerenciadorreceitas

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView

class MainActivity : AppCompatActivity(), AdapterReceitas.OnClick{

    private var recipeList = mutableListOf<Receita>()
    private var rvReceitas: SwipeableRecyclerView? = null
    private var adapterReceitas:AdapterReceitas? = null
    private var ibMore:ImageButton? = null
    private var receitaDAO:ReceitaDAO? = null
    private var llInfo:LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receitaDAO = ReceitaDAO(this)
        recipeList = receitaDAO!!.getListRecipes()
        rvReceitas = findViewById(R.id.rv_receitas)
        ibMore = findViewById(R.id.ib_more)
        llInfo = findViewById(R.id.ll_info)

//        carregaLista()
        configReciclerView()
        listenerClicks()
    }

    override fun onStart() {
        super.onStart()
        configReciclerView()
    }

    private fun configReciclerView(){
        recipeList.clear()
        recipeList = receitaDAO!!.getListRecipes()

        existeReceita()

        rvReceitas?.layoutManager = LinearLayoutManager(this)
        rvReceitas?.setHasFixedSize(true)
        adapterReceitas = AdapterReceitas(recipeList,this)
        rvReceitas!!.adapter = adapterReceitas

        rvReceitas!!.setListener(object : SwipeLeftRightCallback.Listener{
            override fun onSwipedLeft(position: Int) {
                receitaDAO!!.deletarReceita(recipeList[position])
                recipeList.removeAt(position)
                adapterReceitas!!.notifyItemRemoved(position)

                existeReceita()
            }

            override fun onSwipedRight(position: Int) {

            }

        })
    }

//    fun carregaLista(){
//        val r1 = Receita()
//        r1.id = 1
//        r1.receita = "receita"
//        r1.descricao = "desc"
//        r1.ingredientes = "ing,ing"
//
//        val r2 = Receita()
//        r2.id = 2
//        r2.receita = "receita"
//        r2.descricao = "desc"
//        r2.ingredientes = "ing,ing"
//
//        recipeList.add(r1)
//        recipeList.add(r2)
//    }

    private fun listenerClicks(){
        ibMore?.setOnClickListener{
            val popupMenu = PopupMenu(this,ibMore)
            popupMenu.menuInflater.inflate(R.menu.menu_toolbar,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_add){
                   startActivity(Intent(this,FormActivity::class.java))
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    private fun existeReceita(){
        if (recipeList.size == 0){
            llInfo!!.visibility = View.VISIBLE
        }else{
            llInfo!!.visibility = View.GONE
        }
    }

    override fun onClickListener(receita: Receita) {
        val intent = Intent(this, RecipeItemActivity::class.java)
        intent.putExtra("receita", receita)
        startActivity(intent)
    }
}