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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Produto
import java.net.URI
import java.security.Permission

class FormAnuncioActivity : AppCompatActivity() {
    private lateinit var ibSalvar: ImageButton
    private lateinit var ibVoltar: ImageButton
    private lateinit var etDescricao: EditText
    private lateinit var etTitulo: EditText
    private lateinit var etQuarto: EditText
    private lateinit var etBanheiro: EditText
    private lateinit var etGaragem: EditText
    private lateinit var cbStatus: CheckBox


    private lateinit var ivFoto: ImageView
    private lateinit var caminhoImagem: String
    private var imagem: Bitmap? = null
    private var produto = Produto()

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_anuncio)

        resultLauncher()
        initComponets()
        configClicks()
    }

    override fun onResume() {
        super.onResume()
        ivFoto.setImageBitmap(imagem)
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
            .setDeniedTitle("Permissões Negadas")
            .setDeniedMessage("Permissão negada para acessar galeria, deseja permitir?")
            .setDeniedCloseButtonText("Não").setGotoSettingButtonText("Sim")
            .setPermissions(*permissoes.toTypedArray())
            .check()
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
    }

    private fun initComponets() {
        var tituloToolbar: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tituloToolbar.text = "Salvar Anúncio"
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
                etDescricao.error = "Informe um descrição"
            }
            quarto.isEmpty() -> {
                etQuarto.requestFocus()
                etQuarto.error = "Informe o número de quartos"
            }
            banheiro.isEmpty() -> {
                etBanheiro.requestFocus()
                etBanheiro.error = "Informe o número de banheiros"
            }
            garagem.isEmpty() -> {
                etGaragem.requestFocus()
                etGaragem.error = "Informe o número de garagens"
            }
            else -> {
                produto.titulo = titulo
                produto.descricao = descricao
                produto.quarto = quarto
                produto.banheiro = banheiro
                produto.garagem = garagem
                produto.status = cbStatus.isChecked

            }
        }
    }
}