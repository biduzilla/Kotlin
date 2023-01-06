package com.toddy.gerenciadorreceitas.activity

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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita
import com.toddy.gerenciadorreceitas.ReceitaDAO

class FormActivity : AppCompatActivity() {
    private lateinit var ivAddFoto: ImageView
    private lateinit var checkBox: CheckBox
    private lateinit var progressBar: ProgressBar
    private lateinit var etReceita: EditText
    private lateinit var etDescricao: EditText
    private lateinit var etIngredientes: EditText
    private lateinit var btnSalvar: Button
    private lateinit var ivVoltar: ImageView
    private lateinit var toolbarText: TextView

    private var caminhoImagem: String? = null
    private var imagem: Bitmap? = null
    private var receita = Receita()

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        startResult()
        initComponets()
        isUpdate()

        clickListener()
    }

    override fun onResume() {
        super.onResume()
        if (imagem != null){
            ivAddFoto.setImageBitmap(imagem)
        }
    }

    private fun initComponets() {
        ivAddFoto = findViewById(R.id.iv_add_foto)
        progressBar = findViewById(R.id.progress_bar)
        checkBox = findViewById(R.id.checkbox)
        etReceita = findViewById(R.id.et_receita)
        etDescricao = findViewById(R.id.et_descricao)
        etIngredientes = findViewById(R.id.et_ingredientes)
        btnSalvar = findViewById(R.id.btn_salvar)
        ivVoltar = findViewById(R.id.iv_back)
        toolbarText = findViewById(R.id.toolbar_txt)
    }

    private fun editReceita() {
        etReceita?.setText(receita.receita)
        etDescricao?.setText(receita.descricao)
        etIngredientes?.setText(receita.ingredientes)
    }

    private fun isUpdate() {
        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            receita = bundle.getSerializable("receita") as Receita
            btnSalvar.text = "Atualizar"
            toolbarText.text = "Atualizar Receita"

            editReceita()

        }
    }

    private fun clickListener() {
        ivVoltar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    val localImage: Uri? = data?.data

                    caminhoImagem = localImage.toString()

                    imagem = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(baseContext.contentResolver, localImage)
                    } else {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(baseContext.contentResolver, localImage!!)
                        ImageDecoder.decodeBitmap(source)
                    }
                    Log.i("Caminho Imagem", caminhoImagem!!)
                }
            }
    }

    fun verificarPermissao(view: View) {
        val permission: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                abrirGaleria()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@FormActivity, "Error ao abrir galeria", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        showDialogPermission(permission, listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
    }

    private fun showDialogPermission(
        permissionListener: PermissionListener, permissoes: List<String>
    ) {
        TedPermission.create().setPermissionListener(permissionListener)
            .setDeniedTitle("Permissão Negada")
            .setDeniedMessage("Permissão negada para acessar galeria, deseja permitir?")
            .setDeniedCloseButtonText("Não").setGotoSettingButtonText("Sim")
            .setPermissions(*permissoes.toTypedArray()).check()
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher!!.launch(intent)
    }

    fun salvarProduto(view: View) {
        val nomeReceita: String = etReceita.text.toString()
        val descricao: String = etDescricao.text.toString()
        val ingredientes: String = etIngredientes.text.toString()
        val checked: Boolean = checkBox.isChecked

        when {
            nomeReceita.isEmpty() -> {
                etReceita.requestFocus()
                etReceita.error = "Campo Obrigatório"
            }
            descricao.isEmpty() -> {
                etDescricao.requestFocus()
                etDescricao.error = "Campo Obrigatório"
            }
            ingredientes.isEmpty() -> {
                etIngredientes.requestFocus()
                etIngredientes.error = "Campo Obrigatório"
            }
            !ingredientes.contains(",") -> {
                etIngredientes.requestFocus()
                etIngredientes.error = "Separe os ingredientes por vírgula"
            }
            else -> {

            }
        }
    }
}