package com.test.headytest.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.headytest.R
import com.test.headytest.di.MyApp
import com.test.helpers.googleutils.DaggerViewModelFactory
import com.test.helpers.utils.Status
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    lateinit var mMainActivityViewModel: MainActivityViewModel

//    private lateinit var mBinding:

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as MyApp).appComponent.inject(this@MainActivity)
        mMainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        init()
    }

    private fun init() {
        getData()
    }

    private fun getData() {
        mMainActivityViewModel.getData().observe(this@MainActivity, Observer { response ->
            when(response?.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    response.data?.let {
                        Log.d("temp", it[0].name)
                    }
                }

                Status.ERROR -> {

                }

                Status.UNSUCCESSFUL -> {

                }
            }

        })
    }
}
