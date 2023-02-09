package com.toddy.olxclone.utils

import com.toddy.olxclone.model.Estado

class EstadoList {

    companion object {
        fun getList(): MutableList<Estado> {
            val estadoList = mutableListOf<Estado>()
            estadoList.add(Estado("Brasil", ""))
            estadoList.add(Estado("Acre", "AC"))
            estadoList.add(Estado("Alagoas", "AL"))
            estadoList.add(Estado("Amapá", "AP"))
            estadoList.add(Estado("Amazonas", "AM"))
            estadoList.add(Estado("Bahia", "BA"))
            estadoList.add(Estado("Ceará", "CE"))
            estadoList.add(Estado("Distrito Federal", "DF"))
            estadoList.add(Estado("Espírito Santo", "ES"))
            estadoList.add(Estado("Goiás", "GO"))
            estadoList.add(Estado("Maranhão", "MA"))
            estadoList.add(Estado("Mato Grosso", "MT"))
            estadoList.add(Estado("Mato Grosso do Sul", "MS"))
            estadoList.add(Estado("Minas Gerais", "MG"))
            estadoList.add(Estado("Pará", "PA"))
            estadoList.add(Estado("Paraíba", "PB"))
            estadoList.add(Estado("Paraná", "PR"))
            estadoList.add(Estado("Pernambuco", "PE"))
            estadoList.add(Estado("Piauí", "PI"))
            estadoList.add(Estado("Rio de Janeiro", "RJ"))
            estadoList.add(Estado("Rio Grande do Norte", "RN"))
            estadoList.add(Estado("Rio Grande do Sul", "RS"))
            estadoList.add(Estado("Rondônia", "RO"))
            estadoList.add(Estado("Roraima", "RR"))
            estadoList.add(Estado("Santa Catarina", "SC"))
            estadoList.add(Estado("São Paulo", "SP"))
            estadoList.add(Estado("Sergipe", "SE"))
            estadoList.add(Estado("Tocantins", "TO"))

            return estadoList
        }
    }
}