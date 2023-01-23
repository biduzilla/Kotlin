package com.toddy.olxclone.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.santalu.maskara.widget.MaskEditText
import com.toddy.olxclone.R
import com.toddy.olxclone.api.CepService
import com.toddy.olxclone.model.Categoria
import com.toddy.olxclone.model.Endereco
import com.toddy.olxclone.model.Local
import okhttp3.Request
import org.w3c.dom.Text
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton
    private lateinit var etPreco: CurrencyEditText
    private lateinit var btnCategoria: Button
    private lateinit var etCep: MaskEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLocal: TextView
    private lateinit var btnCamera: Button
    private lateinit var btnGaleria: Button
    private lateinit var btnClose: Button
    private lateinit var ivImg1: ImageView
    private lateinit var ivImg2: ImageView
    private lateinit var ivImg3: ImageView


    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var retrofit: Retrofit? = null

    private var categoria = Categoria()
    private var endereco = Endereco()
    private var local: Local? = null

    private var categoriaSelecionada: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponents()
        initRetrofit()
        configClicks()
        startResult()
        recuperaEndereco()
    }

    private fun showBottonDialog(requestCode: Int) {
        val modalBottomSheet: View = layoutInflater.inflate(R.layout.layout_botton_sheet, null)
        val bottomSheetDialog: BottomSheetDialog =
            BottomSheetDialog(this, R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(modalBottomSheet)
        bottomSheetDialog.show()

        modalBottomSheet.findViewById<Button>(R.id.btn_camera).setOnClickListener {
            bottomSheetDialog.dismiss()
            verificaPermissaoCamera(requestCode)
        }
        modalBottomSheet.findViewById<Button>(R.id.btn_galeira).setOnClickListener {
            bottomSheetDialog.dismiss()
            verificaPermissaoGaleria(requestCode)
        }
        modalBottomSheet.findViewById<Button>(R.id.btn_close).setOnClickListener {
            bottomSheetDialog.dismiss()
            Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificaPermissaoCamera(requestCode: Int) {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                abriCamera(requestCode)
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@FormActivity, "Permissão Negada", Toast.LENGTH_SHORT).show()
            }

        }
        showDialogPermission(
            permissionListener,
            listOf(Manifest.permission.CAMERA),
            "Deseja ativar a permissão?"
        )
    }


    private fun verificaPermissaoGaleria(requestCode: Int) {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                abrirGaleira(requestCode)
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@FormActivity, "Permissão Negada", Toast.LENGTH_SHORT).show()
            }

        }
        showDialogPermission(
            permissionListener,
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            "Deseja ativar a permissão?"
        )
    }

    private fun abrirGaleira(requestCode: Int) {

    }

    private fun abriCamera(requestCode: Int) {

    }

    private fun showDialogPermission(
        permissionListener: PermissionListener,
        permissoes: List<String>,
        msg: String
    ) {
        TedPermission.create().setPermissionListener(permissionListener)
            .setDeniedTitle("PErmissão Negada")
            .setDeniedMessage(msg)
            .setDeniedCloseButtonText("Não").setGotoSettingButtonText("Sim")
            .setPermissions(*permissoes.toTypedArray())
            .check()
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    categoria = it.data!!.getSerializableExtra("categoriaSelecionada") as Categoria
                    categoriaSelecionada = categoria.nome
                    btnCategoria.text = categoriaSelecionada
                } else {

                }
            }
    }

    private fun recuperaEndereco() {
        configCep()
        FirebaseDatabase.getInstance().reference
            .child("enderecos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    endereco = snapshot.getValue(Endereco::class.java)!!
                    etCep.setText(endereco.cep)
                    progressBar.visibility = View.GONE

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun configCep() {
        etCep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val cep: String = p0.toString().replace("_", "").replace("-", "")

                if (cep.length == 8) {
                    buscarEndereco(cep)
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun buscarEndereco(cep: String) {
        progressBar.visibility = View.VISIBLE

        val cepService: CepService = retrofit!!.create(CepService::class.java)
        val call: Call<Local> = cepService.getCep(cep)
        call.enqueue(object : Call<Local>, Callback<Local> {
            override fun onResponse(call: Call<Local>, response: Response<Local>) {
                if (response.isSuccessful) {
                    local = response.body()!!
                    if (local!!.localidade == null) {
                        Toast.makeText(this@FormActivity, "Cep Inválido", Toast.LENGTH_SHORT).show()
                        tvLocal.text = ""
                    } else {
                        configEndereco()
                    }
                } else {
                    Toast.makeText(
                        this@FormActivity,
                        "Tente novamente mais tarde",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }

            override fun onFailure(call: Call<Local>, t: Throwable) {
                Toast.makeText(this@FormActivity, "Tente novamente mais tarde", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun clone(): Call<Local> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<Local> {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<Local>) {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

        })

    }

    private fun configEndereco() {
        val endereco: String =
            local!!.localidade + ", " + local!!.bairro + " - DDD " + local!!.ddd
        tvLocal.text = endereco
    }

    fun selecionarCategoria(view: View) {
        val intent = Intent(this, CategoriasActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun initComponents() {
        tituloToolbar = findViewById(R.id.text_toolbar)
        tituloToolbar.text = "Novo Anúncio"

        btnCategoria = findViewById(R.id.btn_categorias)
        ibVoltar = findViewById(R.id.ib_voltar)
        etCep = findViewById(R.id.et_cep)
        progressBar = findViewById(R.id.progress_bar)
        tvLocal = findViewById(R.id.tv_local)
//        btnCamera = findViewById(R.id.btn_camera)
//        btnGaleria = findViewById(R.id.btn_galeira)
//        btnClose = findViewById(R.id.btn_close)
        ivImg1 = findViewById(R.id.img_1)
        ivImg2 = findViewById(R.id.img_2)
        ivImg3 = findViewById(R.id.img_3)

        etPreco = findViewById(R.id.et_preco)
        etPreco.locale = Locale("PT", "br")
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }

        ivImg1.setOnClickListener {
            showBottonDialog(0)
        }
        ivImg2.setOnClickListener {
            showBottonDialog(1)
        }
        ivImg3.setOnClickListener {
            showBottonDialog(2)
        }
    }

    private fun initRetrofit() {
        retrofit = Retrofit
            .Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}