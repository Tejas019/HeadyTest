package com.test.headytest.repository

import com.test.headytest.ui.home.Category
import com.test.headytest.ui.product.Product
import com.test.headytest.ui.product.Tax
import com.test.headytest.ui.product.variants.Variant
import io.realm.annotations.RealmModule

@RealmModule(library = true, classes = [Category::class, Product::class, Tax::class, Variant::class])
public class AppRealmModule {
}