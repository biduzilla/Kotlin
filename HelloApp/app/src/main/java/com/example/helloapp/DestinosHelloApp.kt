package com.example.helloapp

sealed class DestinosHelloApp (val rota:String){
    object LoginGraph : DestinosHelloApp("grafico_login")
    object HomeGraph : DestinosHelloApp("grafico_home")
    object SplashScreen : DestinosHelloApp("splashScreen")
    object ListaContatos : DestinosHelloApp("lista_contatos")
    object FormularioLogin : DestinosHelloApp("formulario_login")
    object Login : DestinosHelloApp("login")
}