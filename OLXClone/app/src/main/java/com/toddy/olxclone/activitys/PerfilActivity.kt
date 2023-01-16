package com.toddy.olxclone.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermissionActivity
import com.gun0912.tedpermission.normal.TedPermission
import com.santalu.maskara.widget.MaskEditText
import com.squareup.picasso.Picasso
import com.toddy.olxclone.R
import com.toddy.olxclone.model.User

class PerfilActivity : AppCompatActivity() {
    private lateinit var ivFoto: ImageView
    private lateinit var etNome: EditText
    private lateinit var etTelefone: MaskEditText
    private lateinit var etEmail: EditText
    private lateinit var progressBar: ProgressBar
    private var caminhoImagem: String? = null

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    private var imagem: Bitmap? = null
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        initComponents()
        configClicks()
        startResult()
        recuperaPerfil()
    }

    override fun onResume() {
        super.onResume()
        if (imagem != null) {
            ivFoto.setImageBitmap(imagem)
        }
        progressBar.visibility = View.GONE
    }

    private fun salvarImagemPerfil() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference
            .child("imagens")
            .child("perfil")
            .child(FirebaseAuth.getInstance().currentUser!!.uid + "jpeg")

        val uploadTask: UploadTask = storageReference.putFile(Uri.parse(caminhoImagem))
        uploadTask.addOnSuccessListener {
            storageReference.downloadUrl.addOnCompleteListener { task ->
                val urlImagem: String = task.result.toString()
                user.imagemPerfil = urlImagem
                user.salvar(progressBar, this)

            }
        }.addOnFailureListener {
            Toast.makeText(this, "Erro no upload, tente mais tarde", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configDados() {
        etNome.setText(user.nome)
        etTelefone.setText(user.telefone)
        etEmail.setText(user.email)

        if (user.imagemPerfil != null){
            Picasso.get().load(user.imagemPerfil).into(ivFoto)
        }
    }

    private fun recuperaPerfil() {
        progressBar.visibility = View.VISIBLE
        FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        user = snapshot.getValue(User::class.java)!!
                        configDados()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun validaDados(view: View) {
        val nome: String = etNome.text.toString()
        val telefone: String = etTelefone.unMasked
        val email: String = etEmail.text.toString()

        when {
            nome.isEmpty() -> {
                etNome.requestFocus()
                etNome.error = "Campo Obrigatório"
            }
            telefone.isEmpty() -> {
                etTelefone.requestFocus()
                etTelefone.error = "Campo Obrigatório"
            }
            telefone.length != 11 -> {
                etTelefone.requestFocus()
                etTelefone.error = "Telefone Inválido"
            }
            else -> {
                progressBar.visibility = View.VISIBLE
                user.nome = nome
                user.telefone = telefone
                if (caminhoImagem != null) {
                    salvarImagemPerfil()
                } else {
                    user.salvar(progressBar, this)
                }
            }
        }
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    val imagemSelecionada: Uri? = data?.data

                    caminhoImagem = imagemSelecionada.toString()

                    imagem = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            baseContext.contentResolver,
                            imagemSelecionada
                        )
                    } else {
                        val source: ImageDecoder.Source = ImageDecoder.createSource(
                            baseContext.contentResolver,
                            imagemSelecionada!!
                        )
                        ImageDecoder.decodeBitmap(source)
                    }
                }
            }
    }

    private fun configClicks() {
        ivFoto.setOnClickListener {
            verificaPermissaoGaleria()
        }

        this.findViewById<ImageButton>(R.id.ib_voltar).setOnClickListener { finish() }
    }

    private fun verificaPermissaoGaleria() {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                abrirGaleria()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@PerfilActivity, "Permissões negadas", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.create().setPermissionListener(permissionListener)
            .setDeniedTitle("Permissão Negada")
            .setDeniedMessage("Permissão negada para acessar galeria, deseja permitir?")
            .setDeniedCloseButtonText("Não")
            .setGotoSettingButtonText("Sim")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher!!.launch(intent)

    }

    private fun initComponents() {
        this.findViewById<ImageButton>(R.id.ib_voltar).setOnClickListener { finish() }

        this.findViewById<TextView>(R.id.text_toolbar).text = ""

        ivFoto = findViewById(R.id.imagem_perfil)
        etNome = findViewById(R.id.edit_nome)
        etTelefone = findViewById(R.id.edit_telefone)
        etEmail = findViewById(R.id.edit_email)
        progressBar = findViewById(R.id.progress_bar)
    }
}