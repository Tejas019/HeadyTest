package com.test.headytest.ui.product.variants

interface VariantDao {
    fun saveVariants(variants: List<Variant>)
}