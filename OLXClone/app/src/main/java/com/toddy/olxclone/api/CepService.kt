package com.toddy.olxclone.api

import com.toddy.olxclone.model.Local
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {

    @GET("{cep}/json/")
    fun getCep(@Path("cep") cep:String): Call<Local>
}