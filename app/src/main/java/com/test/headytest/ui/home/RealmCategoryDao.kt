package com.test.headytest.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.realm.Realm

class RealmCategoryDao(val realm: Realm): CategoryDao {

    override fun getCategories(): LiveData<List<Category>?> {
        val live = MutableLiveData<List<Category>?>()
        val categoryList = realm.where(Category::class.java).findAll()
        live.value = realm.copyFromRealm(categoryList)
        return live
    }

    override fun saveCategories(categories: List<Category>) {
        realm.executeTransaction {
            it.insertOrUpdate(categories)
        }
    }
}