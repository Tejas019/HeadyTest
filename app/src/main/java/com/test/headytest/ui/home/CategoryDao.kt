package com.test.headytest.ui.home

import android.arch.lifecycle.LiveData

interface CategoryDao {
    fun saveCategories(categories: List<Category>)
    fun getCategories(): LiveData<List<Category>?>
}