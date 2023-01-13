package com.toddy.olxclone.utils

class FirebaseHelper {

    companion object {
        fun validaErros(error: String):String {
            var msg: String = ""

            when {
                error.contains("There is no user record corresponding to this identifier")
                -> {
                    msg = "Nenhuma conta encontrada com este e-mail.";
                }
                error.contains("The email address is badly formatted")
                -> {
                    msg = "Insira um e-mail válido.";
                }
                error.contains("The password is invalid or the user does not have a password")
                -> {
                    msg = "Senha inválida, tente novamente.";
                }
                error.contains("The email address is already in use by another account")
                -> {
                    msg = "Este e-mail já está em uso.";
                }
                error.contains("Password should be at least 6 characters")
                -> {
                    msg = "Insira uma senha mais forte.";
                }
                else -> msg = "Error"
            }

            return msg
        }
    }
}