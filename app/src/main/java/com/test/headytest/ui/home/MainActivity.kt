package com.test.headytest.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.test.headytest.R
import com.test.headytest.di.MyApp
import com.test.helpers.googleutils.DaggerViewModelFactory
import com.test.helpers.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory
    lateinit var mMainActivityViewModel: MainActivityViewModel

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
                    pb_loader.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    pb_loader.visibility = View.GONE
                    response.data?.let {allCategories ->
                        if(allCategories.isNotEmpty()) {
                            val mainCategories = mutableListOf<Category>()
                            val childCategories = mutableListOf<Category>()
                            allCategories.forEach {
                                if(it.child_categories!!.isNotEmpty())
                                    mainCategories.add(it)
                                else
                                    childCategories.add(it)
                            }

                            val childCategoryMap = HashMap<Int, List<Category>>()
                            mainCategories.forEach { mainCategory -> childCategoryMap.put(mainCategory.id!!, childCategories.filter { mainCategory.child_categories!!.contains(it.id) }) }

                            val categoriesAdapter = CategoriesAdapter(this@MainActivity, mainCategories, childCategoryMap)
                            lv_categories.setAdapter(categoriesAdapter)
                        }
                    }
                }

                Status.ERROR -> {
                    pb_loader.visibility = View.GONE
                    showMessage(response.message!!)
                }

                Status.UNSUCCESSFUL -> {
                    pb_loader.visibility = View.GONE
                    showMessage(response.message!!)
                }
            }
        })
    }

    fun showMessage(message: String) {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(getString(R.string.ok), null)
        alertDialog.show()
    }
}
