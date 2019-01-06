package com.test.headytest.ui.home

import com.test.headytest.ui.product.Product
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Category: RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var name: String? = null
    var products: RealmList<Product>? = null
    var child_categories: RealmList<Int>? = null
}