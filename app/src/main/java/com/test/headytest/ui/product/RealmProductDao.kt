package com.test.headytest.ui.product

import io.realm.Realm

class RealmProductDao(val realm: Realm): ProductDao {

    override fun saveProducts(products: List<Product>) {
        realm.executeTransaction {
            it.insertOrUpdate(products)
        }
    }
}