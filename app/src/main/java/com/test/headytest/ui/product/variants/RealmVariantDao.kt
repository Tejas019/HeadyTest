package com.test.headytest.ui.product.variants

import io.realm.Realm

class RealmVariantDao(val realm: Realm): VariantDao {

    override fun saveVariants(variants: List<Variant>) {
        realm.executeTransaction {
            it.insertOrUpdate(variants)
        }
    }
}