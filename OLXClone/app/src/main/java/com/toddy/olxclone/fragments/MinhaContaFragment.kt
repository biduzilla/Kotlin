package com.toddy.olxclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.toddy.olxclone.R
import com.toddy.olxclone.activitys.EnderecoActivity
import com.toddy.olxclone.activitys.PerfilActivity

class MinhaContaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_minha_conta, container, false)

        configClicks(view)

        return view
    }

    private fun configClicks(view: View) {
        view.findViewById<ConstraintLayout>(R.id.menu_perfil).setOnClickListener {
            startActivity(Intent(view.context, PerfilActivity::class.java))
        }

        view.findViewById<ConstraintLayout>(R.id.menu_endereco).setOnClickListener {
            startActivity(Intent(view.context, EnderecoActivity::class.java))
        }
    }
}