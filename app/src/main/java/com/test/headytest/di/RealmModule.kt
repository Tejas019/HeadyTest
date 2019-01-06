package com.test.headytest.di

import android.content.Context
import android.util.Base64
import com.test.headytest.repository.AppRealmModule
import com.tejas.helpers.constants.Constants.Companion.REALM_DB_MYAPP
import com.tejas.helpers.constants.Constants.Companion.REALM_ENCRYPTION_KEY
import com.test.headytest.ui.home.CategoryDao
import com.test.headytest.ui.home.RealmCategoryDao
import com.test.headytest.ui.product.ProductDao
import com.test.headytest.ui.product.RealmProductDao
import com.test.headytest.ui.product.variants.RealmVariantDao
import com.test.headytest.ui.product.variants.VariantDao
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*
import javax.inject.Singleton

@Module
class RealmModule constructor(private val mContext: Context) {

    private fun init(context: Context) {
        Realm.init(context)
        val configuration = RealmConfiguration.Builder()
        configuration
                .name(REALM_DB_MYAPP)
                .encryptionKey(getEncryptionKey())
                .modules(AppRealmModule())
                .deleteRealmIfMigrationNeeded()
        Realm.setDefaultConfiguration(configuration.build())
    }

    private fun getEncryptionKey(): ByteArray {
        val prefs = mContext.getSharedPreferences(mContext.packageName, Context.MODE_PRIVATE)
        var encryptionKey = prefs.getString(REALM_ENCRYPTION_KEY, "")
        if (encryptionKey.isNullOrEmpty()) {
            val rand = Random()
            val bytesArray = ByteArray(64)
            rand.nextBytes(bytesArray)
            encryptionKey = Base64.encodeToString(bytesArray, Base64.DEFAULT)
        }
        val finalEncryptionKey = Base64.decode(encryptionKey, Base64.DEFAULT)
        prefs.edit().putString(REALM_ENCRYPTION_KEY, encryptionKey).apply()
        return finalEncryptionKey
    }


    @Singleton
    @Provides
    fun provideRealm(): Realm {
        init(mContext)
        try {
            return Realm.getDefaultInstance()
        } catch (e: Exception) {
            e.printStackTrace()
            return Realm.getDefaultInstance()
        }
    }

    @Singleton
    @Provides
    fun provideCategoryDao(realm: Realm): CategoryDao = RealmCategoryDao(realm)

    @Singleton
    @Provides
    fun provideProductDao(realm: Realm): ProductDao = RealmProductDao(realm)

    @Singleton
    @Provides
    fun provideVariantDao(realm: Realm): VariantDao = RealmVariantDao(realm)
}