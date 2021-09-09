package com.aya.walmarttask.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aya.walmarttask.data.Dao.ProductDao
import com.aya.walmarttask.data.model.Product
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase(){

    private  lateinit var db: AppDatabase
    private lateinit var dao: ProductDao


    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        //Temporary for testing
        db= Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.productDao()
    }

    // close DB after testing
    @After
    fun closeDP(){

        db.close()
    }


    @Test
    fun writeAndReadProduct() = runBlocking{

        //Test Data
        val productList = listOf(Product("ff",true,"test",
        "343","image","TV",4, 4.5F,"TV"),
            Product("ff",true,"test",
                "343","image","TV",4,4.5F,"TV"))


        dao.insertAll(productList)

        val products = dao.getAll()
        assertThat(products.contains(products).toString() ,true)


    }
}