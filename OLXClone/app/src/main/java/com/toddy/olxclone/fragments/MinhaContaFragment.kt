package com.toddy.olxclone.adapter.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.EnderecoActivity
import com.toddy.olxclone.activitys.MainActivity
import com.toddy.olxclone.activitys.PerfilActivity
import com.toddy.olxclone.auth.LoginActivity
import com.toddy.olxclone.model.User

class MinhaContaFragment : Fragment() {

    private lateinit var tvTxtConta: TextView
    private lateinit var tvTxtUser: TextView
    private lateinit var ivPerfil: ImageView
    private var user = User()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_minha_conta, container, false)

        initComponents(view)
        configClicks(view)
        recuperaUsuario()

        return view
    }

    override fun onStart() {
        super.onStart()
        recuperaUsuario()
    }

    private fun recuperaUsuario() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseDatabase.getInstance().reference
                .child("usuarios")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!
                        configConta()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
    }

    private fun configConta() {
        tvTxtUser.text = user.nome
        if (user.imagemPerfil != null) {
            ivPerfil.scaleType = ImageView.ScaleType.CENTER_CROP
            Picasso.get().load(user.imagemPerfil)
                .placeholder(R.drawable.loading)
                .into(ivPerfil)
        }
    }

    private fun initComponents(view: View) {
        ivPerfil = view.findViewById(R.id.iv_perfil)
        tvTxtConta = view.findViewById(R.id.tv_conta)
        tvTxtUser = view.findViewById(R.id.txt_user)

        if (FirebaseAuth.getInstance().currentUser != null) {
            tvTxtConta.text = "Sair"
        } else {
            "Clique Aqui"
        }
    }

    private fun configClicks(view: View) {
        view.findViewById<ConstraintLayout>(R.id.menu_perfil).setOnClickListener {
            redirecionaUser(PerfilActivity::class.java)
        }

        view.findViewById<ConstraintLayout>(R.id.menu_endereco).setOnClickListener {
            redirecionaUser(EnderecoActivity::class.java)
        }

        tvTxtConta.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser != null) {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(activity, MainActivity::class.java))
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }

    private fun redirecionaUser(cls: Class<*>){
        if (FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(activity, cls))
        }else{
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}