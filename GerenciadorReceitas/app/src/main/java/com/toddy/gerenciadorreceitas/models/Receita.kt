package com.toddy.gerenciadorreceitas.models

class Receita:java.io.Serializable {
    var id:String = ""
    var idUser:String = ""
    var receita:String = ""
    var descricao:String = ""
    var ingredientes:String = ""
    var isSalgada:Boolean = false
}