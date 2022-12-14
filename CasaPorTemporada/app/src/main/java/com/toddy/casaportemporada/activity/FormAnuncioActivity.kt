package com.toddy.casaportemporada.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.squareup.picasso.Picasso
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Anuncio

class FormAnuncioActivity : AppCompatActivity() {
    private lateinit var ibSalvar: ImageButton
    private lateinit var ibVoltar: ImageButton
    private lateinit var etDescricao: EditText
    private lateinit var etTitulo: EditText
    private lateinit var etQuarto: EditText
    private lateinit var etBanheiro: EditText
    private lateinit var etGaragem: EditText
    private lateinit var tvToolBarTittle: TextView
    private lateinit var cbStatus: CheckBox
    private lateinit var progressBar: ProgressBar


    private lateinit var ivFoto: ImageView
    private var caminhoImagem: String? = null
    private var imagem: Bitmap? = null
    private var anuncio = Anuncio()

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_anuncio)

        resultLauncher()
        initComponets()
        configClicks()

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            anuncio = bundle.getSerializable("anuncio") as Anuncio
            configDados()
        }
    }

    private fun configDados() {
        tvToolBarTittle.text = "Atualizar An??ncio"
        Picasso.get().load(anuncio.urlImage).into(ivFoto)
        etTitulo.setText(anuncio.titulo)
        etDescricao.setText(anuncio.descricao)
        etQuarto.setText(anuncio.quarto)
        etGaragem.setText(anuncio.garagem)
        etBanheiro.setText(anuncio.banheiro)
        cbStatus.isChecked = anuncio.status
    }

    override fun onResume() {
        super.onResume()
        if (imagem != null) {
            ivFoto.setImageBitmap(imagem)
        }
    }

    fun veriricaPermissaoGaleira(view: View) {
        val permission: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                abrirGaleria()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@FormAnuncioActivity, "Galeria Error", Toast.LENGTH_SHORT).show()
            }
        }

        showDialogPermission(permission, listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    private fun showDialogPermission(permission: PermissionListener, permissoes: List<String>) {
        TedPermission.create().setPermissionListener(permission)
            .setDeniedTitle("Permiss??es Negadas")
            .setDeniedMessage("Permiss??o negada para acessar galeria, deseja permitir?")
            .setDeniedCloseButtonText("N??o").setGotoSettingButtonText("Sim")
            .setPermissions(*permissoes.toTypedArray()).check()
    }

    private fun resultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    val localImage: Uri? = data?.data

                    caminhoImagem = localImage.toString()

                    imagem = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            baseContext.contentResolver, localImage
                        )
                    } else {
                        val source: ImageDecoder.Source = ImageDecoder.createSource(
                            baseContext.contentResolver, localImage!!
                        )
                        ImageDecoder.decodeBitmap(source)
                    }
                    Log.i("Caminho Imagem", caminhoImagem!!)
                }
            }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher!!.launch(intent)
    }

    private fun configClicks() {
        ibSalvar.setOnClickListener {
            validaDados()
        }
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponets() {

        tvToolBarTittle = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvToolBarTittle.text = "Salvar An??ncio"

        cbStatus = findViewById(R.id.cb_status)
        progressBar = findViewById(R.id.progress_bar)
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        ibSalvar = findViewById(R.id.ib_toolbar_salvar)
        ivFoto = findViewById(R.id.iv_foto)
        etDescricao = findViewById(R.id.et_descricao)
        etTitulo = findViewById(R.id.et_titulo)
        etQuarto = findViewById(R.id.et_quarto)
        etGaragem = findViewById(R.id.et_garagem)
        etBanheiro = findViewById(R.id.et_banheiro)
    }

    private fun validaDados() {
        val titulo: String = etTitulo.text.toString()
        val descricao: String = etDescricao.text.toString()
        val quarto: String = etQuarto.text.toString()
        val banheiro: String = etBanheiro.text.toString()
        val garagem: String = etGaragem.text.toString()

        when {
            titulo.isEmpty() -> {
                etTitulo.requestFocus()
                etTitulo.error = "Informe um titulo"
            }
            descricao.isEmpty() -> {
                etDescricao.requestFocus()
                etDescricao.error = "Informe um descri????o"
            }
            quarto.isEmpty() -> {
                etQuarto.requestFocus()
                etQuarto.error = "Informe o n??mero de quartos"
            }
            banheiro.isEmpty() -> {
                etBanheiro.requestFocus()
                etBanheiro.error = "Informe o n??mero de banheiros"
            }
            garagem.isEmpty() -> {
                etGaragem.requestFocus()
                etGaragem.error = "Informe o n??mero de garagens"
            }
            else -> {
                anuncio.idUser = FirebaseAuth.getInstance().currentUser!!.uid
                anuncio.titulo = titulo
                anuncio.descricao = descricao
                anuncio.quarto = quarto
                anuncio.banheiro = banheiro
                anuncio.garagem = garagem
                anuncio.status = cbStatus.isChecked

                progressBar.visibility = View.VISIBLE

                if (caminhoImagem != null) {
                    salvarImagemAnuncio()

                } else {
                    if (anuncio.urlImage != "") {
                        anuncio.salvarAnuncio()
                        finish()

                    } else {
                        Toast.makeText(this, "Selecione uma imagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun salvarImagemAnuncio() {
        val reference: StorageReference =
            FirebaseStorage.getInstance().reference.child("imagens").child("anuncios")
                .child(anuncio.id + "jpg")

        val uploadTask: UploadTask = reference.putFile(Uri.parse(caminhoImagem))
        uploadTask.addOnSuccessListener {
            reference.downloadUrl.addOnCompleteListener {
                anuncio.urlImage = it.result.toString()
                anuncio.salvarAnuncio()
                progressBar.visibility = View.GONE
                finish()
            }
        }.addOnFailureListener {
            progressBar.visibility = View.GONE
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}