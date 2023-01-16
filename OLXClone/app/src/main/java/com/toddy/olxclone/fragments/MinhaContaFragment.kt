package com.toddy.olxclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.EnderecoActivity
import com.toddy.olxclone.activitys.MainActivity
import com.toddy.olxclone.activitys.PerfilActivity
import com.toddy.olxclone.auth.LoginActivity

class MinhaContaFragment : Fragment() {

    private lateinit var tvTxtConta:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_minha_conta, container, false)

        initComponents(view)
        configClicks(view)

        return view
    }

    private fun initComponents(view: View){
        tvTxtConta = view.findViewById(R.id.tv_conta)
        if (FirebaseAuth.getInstance().currentUser != null){
            tvTxtConta.text = "Sair"
        }else {
            "Clique Aqui"
        }
    }

    private fun configClicks(view: View) {
        view.findViewById<ConstraintLayout>(R.id.menu_perfil).setOnClickListener {
            startActivity(Intent(view.context, PerfilActivity::class.java))
        }

        view.findViewById<ConstraintLayout>(R.id.menu_endereco).setOnClickListener {
            startActivity(Intent(view.context, EnderecoActivity::class.java))
        }

        tvTxtConta.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null){
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(activity, MainActivity::class.java))
            }else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }
}