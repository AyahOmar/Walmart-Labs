package com.aya.walmarttask.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.databinding.ActivityProductDetailsBinding
import com.aya.walmarttask.ui.main.adapter.ProductDetailsAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ProductDetails : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var adapter: ProductDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setupUI()

        //Get Shared Preferences
        val sharedPref = getSharedPreferences("ProductDetails", Context.MODE_PRIVATE)
        val gson = Gson()
        val pos = sharedPref.getInt("Position", 0)
        var json = sharedPref.getString("ProductDetailsObject", "")
        val type: Type = object : TypeToken<List<Product?>?>() {}.type
        val arrayList: List<Product> = gson.fromJson<List<Product>>(json, type)


        val snapHelper: SnapHelper = PagerSnapHelper()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = ProductDetailsAdapter(arrayListOf())
        binding.recyclerView.scrollToPosition(pos)
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter


        retrieveList(arrayList)
    }


    private fun setupUI() {



    }

    private fun retrieveList(products: List<Product>) {
        adapter.apply {
            addProducts(products)
            notifyDataSetChanged()
        }
    }
}