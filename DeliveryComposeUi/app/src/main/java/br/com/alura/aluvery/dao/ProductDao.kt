package br.com.alura.aluvery.dao

import androidx.compose.runtime.mutableStateListOf
import br.com.alura.aluvery.model.Product

class ProductDao {

    companion object {
//        private val products = mutableListOf<Product>(*sampleProducts.toTypedArray())
        private val products = mutableStateListOf<Product>()
    }

    fun products() = products.toList()
    fun save(p: Product) {
        products.add(p)
    }
}