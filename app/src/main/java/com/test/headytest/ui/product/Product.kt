package com.test.headytest.ui.product

import com.test.headytest.ui.product.variants.Variant
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Product: RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var name: String? = null
    var date_added: String? = null
    var tax: Tax? = null
    var order_count: Int? = null
    var view_count: Int? = null
    var shares: Int? = null
    var variants: RealmList<Variant>? = null

    var categoryId: Int? = null
}