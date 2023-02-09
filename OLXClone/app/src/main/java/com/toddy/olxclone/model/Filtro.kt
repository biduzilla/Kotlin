package com.toddy.olxclone.model

data class Filtro(
    var estado: Estado? = null,
    var categoria: String = "",
    var pesquisa: String = "",
    var valorMin: Int = 0,
    var valorMax: Int = 0
)
