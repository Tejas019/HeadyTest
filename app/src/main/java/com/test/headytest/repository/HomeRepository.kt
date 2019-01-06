package com.test.headytest.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.test.headytest.ui.home.Category
import com.test.headytest.ui.home.CategoryDao
import com.test.headytest.ui.home.JsonData
import com.test.headytest.ui.product.Product
import com.test.headytest.ui.product.ProductDao
import com.test.headytest.ui.product.variants.Variant
import com.test.headytest.ui.product.variants.VariantDao
import com.test.helpers.utils.AppExecutors
import com.test.helpers.utils.Resource
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class HomeRepository @Inject constructor(private val mExecutors: AppExecutors, mRetrofit: Retrofit, private val mCategoryDao: CategoryDao, private val mProductDao: ProductDao, private val mVariantDao: VariantDao) {

    private val mApiService = mRetrofit.create(ApiService::class.java)

    fun getData(): LiveData<Resource<List<Category>>> {
        val result = MediatorLiveData<Resource<List<Category>>>()
        val db = mCategoryDao.getCategories()
        result.value = Resource.loading(null)
        result.addSource(db) {categories ->
            if(categories?.isNotEmpty()!!) {
                result.value = Resource.success(categories)
            }
            result.removeSource(db)
        }

        mExecutors.networkIO().execute {
            try {
                val response: Response<JsonData?> = mApiService.getData().execute()
                val isSuccess = response.isSuccessful
                mExecutors.mainThread().execute {
                    if (isSuccess) {
                        val categories = response.body()?.categories
                        val products = mutableListOf<Product>()
                        categories?.forEach { categoryFromApi ->
                            categoryFromApi.products?.let { productsFromApi ->
                                productsFromApi.map { it.categoryId = categoryFromApi.id }
                                products.addAll(productsFromApi)
                                categoryFromApi.products = null
                            }

                        }

                        //Save categories without products in it.
                        categories?.let { mCategoryDao.saveCategories(it) }

                        //Save products here.
                        mProductDao.saveProducts(products)

                        val variants = mutableListOf<Variant>()
                        products.forEach { product ->
                            product.variants?.map { variant -> variant.productId = product.id }
                            variants.addAll(product.variants!!)
                            product.variants = null
                        }

                        mVariantDao.saveVariants(variants)

                        val rankingProducts = mutableListOf<Product>()
                        response.body()?.rankings?.forEach { ranking ->
                            ranking.products?.let { rankingProducts.addAll(it) }
                        }

                        mProductDao.saveProducts(rankingProducts)

                        result.value = Resource.success(categories)

                    } else {
                        result.value = Resource.error(response.message(), null)
                    }
                }
            } catch (e: Exception) {
                mExecutors.mainThread().execute {
                    result.value = Resource.unsuccessful(Resource.getExceptionMessage(e))
                }
            }
        }
        return result
    }
}