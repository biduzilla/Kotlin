package com.toddy.olxclone.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermissionActivity
import com.gun0912.tedpermission.normal.TedPermission
import com.santalu.maskara.widget.MaskEditText
import com.toddy.olxclone.R

class PerfilActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var etNome: EditText
    private lateinit var etTelefone: MaskEditText
    private lateinit var etEmail: EditText
    private lateinit var progressBar: ProgressBar
    private var caminhoImagem:String? = null

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        initComponents()
        configClicks()
        startResult()
    }

    fun validaDados(view: View){
        val nome:String = etNome.text.toString()
        val telefone:String = etTelefone.unMasked
        val email:String = etEmail.text.toString()

        when{
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
            }
        }

    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    val imagemSelecionada: Uri? = data!!.data
                    val imagemRecuperada:Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imagemSelecionada)
                    image.setImageBitmap(imagemRecuperada)

                    caminhoImagem = imagemSelecionada.toString()
                }
            }
    }

    private fun configClicks() {
        image.setOnClickListener {
            verificaPermissaoGaleria()
        }
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

        image = findViewById(R.id.imagem_perfil)
        etNome = findViewById(R.id.edit_nome)
        etTelefone = findViewById(R.id.edit_telefone)
        etEmail = findViewById(R.id.edit_email)
        progressBar = findViewById(R.id.progress_bar)
    }
}