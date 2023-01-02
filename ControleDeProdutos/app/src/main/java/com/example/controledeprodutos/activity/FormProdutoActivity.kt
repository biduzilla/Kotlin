package com.example.controledeprodutos.activity

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
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.controledeprodutos.ProdutoDAO
import com.example.controledeprodutos.models.ProdutoEntity
import com.example.controledeprodutos.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class FormProdutoActivity : AppCompatActivity() {
    private var editProduto: EditText? = null
    private var editQuantidade: EditText? = null
    private var editValor: EditText? = null
    private var produtoDAO: ProdutoDAO? = null
    private var produto = ProdutoEntity()
    private var btnForm: Button? = null
    private var ivImgProduto: ImageView? = null
    private val REQUEST_GALERIA = 100
    private var caminhoImage: String? = null
    private var image: Bitmap? = null
    private var resultLauncher:ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)
        carregaImagem()
        initComponents()
    }

    private fun carregaImagem(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                val localImageSelect: Uri? = data?.data
                caminhoImage = localImageSelect.toString()

                image = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(
                        baseContext.contentResolver,
                        localImageSelect
                    )
                } else {
                    val source: ImageDecoder.Source = ImageDecoder.createSource(
                        baseContext.contentResolver,
                        localImageSelect!!
                    )
                    ImageDecoder.decodeBitmap(source)
                }
                Log.i("Caminho Imagem", caminhoImage!!)
            }
        }
    }


    private fun initComponents() {
        produtoDAO = ProdutoDAO(this)
        ivImgProduto = findViewById(R.id.iv_image_produto)
        editProduto = findViewById(R.id.edit_produto)
        editQuantidade = findViewById(R.id.edit_quantidade)
        editValor = findViewById(R.id.edit_valor)
        btnForm = findViewById(R.id.btn_form)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            produto = bundle.getSerializable("produto") as ProdutoEntity
            btnForm!!.text = "atualizar"
            editProduto()
        }
    }

    fun voltarHome(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun verificaPermicaoGaleira(view: View) {
        val permission: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                openGaleria()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@FormProdutoActivity, "Galeria Error", Toast.LENGTH_SHORT).show()
            }
        }
        showDialogPermission(permission, listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
    }


    private fun openGaleria() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher!!.launch(intent)

        //        startActivityForResult(intent, REQUEST_GALERIA)
    }


    private fun showDialogPermission(listener: PermissionListener, permissoes: List<String>) {
        TedPermission.create().setPermissionListener(listener)
            .setDeniedMessage("Permissão negada para acessar galeria, deseja permitir?")
            .setDeniedTitle("Permissões")
            .setDeniedCloseButtonText("Não").setGotoSettingButtonText("Sim")
            .setPermissions(*permissoes.toTypedArray())
            .check()
    }

    private fun editProduto() {
        editProduto!!.setText(produto.nome)
        editQuantidade!!.setText(produto.estoque.toString())
        editValor!!.setText(produto.valor.toString())
    }

    fun salvarProduto(view: View) {
        val nome: String = editProduto!!.text.toString()
        val quantidade: String = editQuantidade!!.text.toString()
        val valor: String = editValor!!.text.toString()

        if (nome.isNotEmpty()) {
            if (quantidade.isNotEmpty()) {
                val qtd: Int = quantidade.toInt()

                if (qtd >= 1) {
                    if (valor.isNotEmpty()) {
                        val valorProduto: Double = valor.toDouble()
                        if (valorProduto > 0) {

                            produto.nome = nome
                            produto.estoque = qtd
                            produto.valor = valorProduto

                            produto.salvarProduto()
                            finish()

                        } else {
                            editValor!!.requestFocus()
                            editValor!!.error = "Informe um valor maior que 0"
                        }
                    } else {
                        editValor!!.requestFocus()
                        editValor!!.error = "Informe o Valor do Produto"
                    }
                } else {
                    editQuantidade!!.requestFocus()
                    editQuantidade!!.error = "Informe um valor maior que 0"
                }
            } else {
                editQuantidade!!.requestFocus()
                editQuantidade!!.error = "Informe a quantidade do produto"
            }
        } else {
            editProduto!!.requestFocus()
            editProduto!!.error = "Informe o nome do produto"
        }
    }
}