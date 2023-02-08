package com.toddy.olxclone.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.santalu.maskara.widget.MaskEditText
import com.toddy.olxclone.R
import com.toddy.olxclone.api.CepService
import com.toddy.olxclone.model.*
import okhttp3.Request
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton

    private lateinit var ivImg1: ImageView
    private lateinit var ivImg2: ImageView
    private lateinit var ivImg3: ImageView

    private lateinit var etTitulo: EditText
    private lateinit var etDescricao: EditText
    private lateinit var etPreco: CurrencyEditText
    private lateinit var btnCategoria: Button
    private lateinit var etCep: MaskEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLocal: TextView

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var REQUEST_CODE: Int = 0
    private lateinit var currentPhotoPath: String

    private var retrofit: Retrofit? = null

    private var categoria = Categoria()
    private var endereco = Endereco()
    private var anuncio = Anuncio()
    private var local: Local? = null
    private var imagemList = mutableListOf<ImagemData>()
    private var novoAnuncio: Boolean = true

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

    fun validaDados(view: View) {
        val titulo: String = etTitulo.text.toString()
        val descricao: String = etDescricao.text.toString()
        val valor: Double = (etPreco.rawValue / 100).toDouble()

        when {
            titulo.isEmpty() -> {
                etTitulo.requestFocus()
                etTitulo.error = "Campo obrigatório"
            }
            valor < 0 -> {
                etPreco.requestFocus()
                etPreco.error = "Valor Inválido"
            }
            categoriaSelecionada == null -> {
                Toast.makeText(this, "Selecione uma categoria", Toast.LENGTH_SHORT).show()
            }
            descricao.isEmpty() -> {
                etDescricao.requestFocus()
                etDescricao.error = "Campo obrigatório"
            }
            etCep.text!!.isEmpty() || local!!.localidade == null -> {
                Toast.makeText(this, "Digite um Cep Valido", Toast.LENGTH_SHORT).show()
            }
            else ->
                if (anuncio.id != null) {
                    progressBar.visibility = View.VISIBLE

                    anuncio.idUser = FirebaseAuth.getInstance().currentUser!!.uid
                    anuncio.titulo = titulo
                    anuncio.valor = valor
                    anuncio.categoria = categoriaSelecionada as String
                    anuncio.descricao = descricao
                    anuncio.local = local

                    if (novoAnuncio) {
                        if (imagemList.size == 3) {
                            imagemList.forEachIndexed { index, imagem ->
                                salvarImgFireBase(imagem, index - 0)
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Selecione 3 imagens para o anúncio",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    progressBar.visibility = View.GONE
                }

        }
    }

    private fun salvarImgFireBase(imagem: ImagemData, index: Int) {
        val storage:StorageReference = FirebaseStorage.getInstance().reference
            .child("imagens")
            .child("anuncios")
            .child(anuncio.id!!)
            .child("imagem ${index}.jpeg")

        val uploadTask:UploadTask = storage.putFile(Uri.parse(imagem.caminhoImagem))
        uploadTask.addOnSuccessListener {
            storage.downloadUrl.addOnCompleteListener { task ->
                if (novoAnuncio){
                    anuncio.imagens.add(index, task.result.toString())
                }else{
                    anuncio.imagens[index] = task.result.toString()
                }

                if (imagemList.size == index + 1){
                    anuncio.salvar(this, novoAnuncio)
                }
            }
        }.addOnFailureListener{
            Toast.makeText(this, it.message,Toast.LENGTH_SHORT).show()
        }
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
                dispatchTakePictureIntent(requestCode)
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
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        REQUEST_CODE = requestCode
        resultLauncher.launch(intent)
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

    private fun configUploads(requestCode: Int, caminho: String) {
        val request = when (requestCode) {
            0, 3 -> 0
            1, 4 -> 1
            2, 5 -> 2
            else -> 3
        }

        val imagemData = ImagemData(caminho, request)

        if (imagemList.size > 0) {
            var encotrou = false
            imagemList.forEach {
                if (it.index == request) {
                    encotrou = true
                }
            }
            if (encotrou) {
                imagemList[request] = imagemData
            } else {
                imagemList.add(imagemData)
            }
        } else {
            imagemList.add(imagemData)
        }
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val bitmap0: Bitmap
                    val bitmap1: Bitmap
                    val bitmap2: Bitmap
                    var imagemSelecionada: Uri? = null

                    it.data?.data.let { uri ->
                        imagemSelecionada = uri
                    }
                    val caminhoImagem: String
                    when {
                        //Galeria
                        REQUEST_CODE <= 2 -> {
                            caminhoImagem = imagemSelecionada.toString()
                            when (REQUEST_CODE) {
                                0 -> {
                                    bitmap0 = if (Build.VERSION.SDK_INT < 28) {
                                        MediaStore.Images.Media.getBitmap(
                                            contentResolver,
                                            imagemSelecionada
                                        )
                                    } else {
                                        val source: ImageDecoder.Source = ImageDecoder.createSource(
                                            contentResolver,
                                            imagemSelecionada!!
                                        )
                                        ImageDecoder.decodeBitmap(source)
                                    }
                                    ivImg1.setImageBitmap(bitmap0)
                                }
                                1 -> {
                                    bitmap1 = if (Build.VERSION.SDK_INT < 28) {
                                        MediaStore.Images.Media.getBitmap(
                                            contentResolver,
                                            imagemSelecionada
                                        )
                                    } else {
                                        val source: ImageDecoder.Source = ImageDecoder.createSource(
                                            contentResolver,
                                            imagemSelecionada!!
                                        )
                                        ImageDecoder.decodeBitmap(source)
                                    }
                                    ivImg2.setImageBitmap(bitmap1)
                                }
                                2 -> {
                                    bitmap2 = if (Build.VERSION.SDK_INT < 28) {
                                        MediaStore.Images.Media.getBitmap(
                                            contentResolver,
                                            imagemSelecionada
                                        )
                                    } else {
                                        val source: ImageDecoder.Source = ImageDecoder.createSource(
                                            contentResolver,
                                            imagemSelecionada!!
                                        )
                                        ImageDecoder.decodeBitmap(source)
                                    }
                                    ivImg3.setImageBitmap(bitmap2)
                                }
                            }
                            configUploads(REQUEST_CODE, caminhoImagem)
                        }
                        //Categoria
                        REQUEST_CODE == 10 -> {
                            if (it.resultCode == Activity.RESULT_OK) {
//                    val data: Intent? = it.data
                                categoria =
                                    it.data!!.getSerializableExtra("categoriaSelecionada") as Categoria
                                categoriaSelecionada = categoria.nome
                                btnCategoria.text = categoriaSelecionada

                            }
                        }
                        //Camera
                        else -> {
                            val file: File = File(currentPhotoPath)
                            caminhoImagem = file.toURI().toString()
                            when (REQUEST_CODE) {
                                3 -> {
                                    ivImg1.setImageURI(Uri.fromFile(file))
                                }
                                4 -> {
                                    ivImg2.setImageURI(Uri.fromFile(file))
                                }
                                5 -> {
                                    ivImg3.setImageURI(Uri.fromFile(file))
                                }
                            }
                            configUploads(REQUEST_CODE, caminhoImagem)
                        }
                    }
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
        REQUEST_CODE = 10
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
        etTitulo = findViewById(R.id.et_titulo)
        etDescricao = findViewById(R.id.et_descricao)

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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent(requestCode: Int) {

        when (requestCode) {
            0 -> {
                REQUEST_CODE = 3
            }
            1 -> {
                REQUEST_CODE = 4
            }
            2 -> {
                REQUEST_CODE = 5
            }
        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.toddy.olxclone.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    resultLauncher.launch(takePictureIntent)
                }
            }
        }
    }

}