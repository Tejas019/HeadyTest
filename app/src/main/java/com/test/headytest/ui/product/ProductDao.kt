package com.test.headytest.ui.product

interface ProductDao {
    fun saveProducts(products: List<Product>)
}