package br.com.alura.aluvery.dao

import br.com.alura.aluvery.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDao {

    companion object {
        //        private val products = mutableListOf<Product>(*sampleProducts.toTypedArray())
        private val products = MutableStateFlow<List<Product>>(emptyList())
    }

    fun products() = products.asStateFlow()
    fun save(p: Product) {
        products.value = products.value + p
    }
}