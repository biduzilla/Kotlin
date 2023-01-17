package com.toddy.olxclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.FormActivity
import com.toddy.olxclone.auth.LoginActivity

class HomeFragment : Fragment() {

    private lateinit var btnNovoAnuncio:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View =  inflater.inflate(R.layout.fragment_home, container, false)

        initComponents(view)
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
    }

    private fun initComponents(view:View){
        btnNovoAnuncio = view.findViewById(R.id.btn_novo_anuncio)
    }
}