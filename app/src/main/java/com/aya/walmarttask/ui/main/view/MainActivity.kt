package com.aya.walmarttask.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aya.walmarttask.data.api.ApiHelper
import com.aya.walmarttask.data.api.RetrofitBuilder
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.data.room.DatabaseBuilder
import com.aya.walmarttask.data.room.DatabaseHelperImpl
import com.aya.walmarttask.databinding.ActivityMainBinding
import com.aya.walmarttask.ui.base.ViewModelFactory
import com.aya.walmarttask.ui.main.adapter.MainAdapter
import com.aya.walmarttask.ui.main.viewmodel.MainViewModel
import com.aya.walmarttask.utils.Status


class MainActivity : AppCompatActivity(){
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        //supportActionBar?.hide()
        supportActionBar?.title = "WalmartLabs"
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(this)
                )
            )
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.adapter = adapter

        setRecyclerViewScrollListener()

    }

    private fun setupObservers() {
        viewModel.getProducts(1,30).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { products -> retrieveList(products) }
                    }
                    Status.ERROR -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                       // Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                      //  viewModel.getProducts()
                       // retrieveList(productss)
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }


    private fun retrieveList(products: List<Product>) {

        adapter.apply {
            addProducts(products)
            notifyDataSetChanged()
        }
    }

    //Loading more items to list
    private fun setRecyclerViewScrollListener() {

        scrollListener = object : RecyclerView.OnScrollListener() {


            var count = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(binding.recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount


                val myLayoutManager: LinearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
                val scrollPosition = myLayoutManager.findLastCompletelyVisibleItemPosition()
                if (totalItemCount == scrollPosition +1 ) {
                    var pageNum = 2
                    var pageSize = 30
                    if(newState == 0){
                        count++
                        if(count <= 8){

                            appendRecyclerView(pageNum,pageSize)
                            Log.d("sasa","sasasas")
                        }else{
                            binding.recyclerView.removeOnScrollListener(scrollListener)
                        }
                    }
                }

            }
        }
        binding.recyclerView.addOnScrollListener(scrollListener)
    }


    private fun appendRecyclerView(pageNum: Int, paceSize: Int) {
        viewModel.getProducts(pageNum,paceSize).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                        resource.data?.let { products -> retrieveList(products) }
                    }
                    Status.ERROR -> {

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }


}


