package com.test.headytest.ui.product.variants

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Variant: RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var color: String? = null
    var size: Int? = null
    var price: Float? = null

    var productId: Int? = null
}