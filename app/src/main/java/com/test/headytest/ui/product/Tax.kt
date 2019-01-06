package com.test.headytest.ui.product

import io.realm.RealmObject

open class Tax: RealmObject() {
    var name: String? = null
    var value: Float? = null
}