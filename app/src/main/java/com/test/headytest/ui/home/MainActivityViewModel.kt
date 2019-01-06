package com.test.headytest.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.test.headytest.repository.HomeRepository
import com.test.helpers.utils.Resource
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(val mHomeRepository: HomeRepository): ViewModel() {

    fun getData(): LiveData<Resource<List<Category>>> {
        return mHomeRepository.getData()
    }

}