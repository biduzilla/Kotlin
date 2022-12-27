package com.toddy.gerenciadorreceitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
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

            }

            override fun onSwipedRight(position: Int) {

            }

        })
    }

    private fun listenerClicks(){
        ibMore!!.setOnClickListener{
            val popupMenu:PopupMenu = PopupMenu(this,ibMore)
            popupMenu.menuInflater.inflate(R.menu.menu_toolbar,popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_add){
                    startActivity(Intent(this,FormActivity::class.java))
                }
                return@setOnMenuItemClickListener true
            }
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
        Toast.makeText(this,receita.id, Toast.LENGTH_SHORT).show()
    }
}