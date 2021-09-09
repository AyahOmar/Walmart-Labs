package com.aya.walmarttask.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.data.repository.MainRepository
import com.aya.walmarttask.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel (private val mainRepository: MainRepository) : ViewModel() {

private val products = MutableLiveData<Resource<List<Product>>>()

    private fun fetchProducts(pageNum: Int , pageSize: Int) {
        viewModelScope.launch {
            products.postValue(Resource.loading(null))

            try {

                val productsFromApi = mainRepository.getProduct(pageNum , pageSize).products
                    mainRepository.deleteHomProductsDB()
                    val productsToInsertInDB = mutableListOf<Product>()
                    for (apiProduct in productsFromApi) {
                        val product = Product(
                           apiProduct.productId,
                            apiProduct.inStock,
                            apiProduct.longDescription,
                            apiProduct.price,
                            apiProduct.productImage,
                            apiProduct.productName,
                            apiProduct.reviewCount,
                            apiProduct.reviewRating,
                            apiProduct.shortDescription
                        )
                        productsToInsertInDB.add(product)
                    }
                    mainRepository.insertProductDB(productsToInsertInDB)
                    products.postValue(Resource.success(productsToInsertInDB))

            } catch (e: Exception) {
                val productsFromDb = mainRepository.getProductDB()
                if (productsFromDb.isNotEmpty()){
                    products.postValue(Resource.success(productsFromDb))
                }else{
                    products.postValue(Resource.error(null,e.message.toString()))
                }


            }
        }
    }

    fun getProducts(pageNum: Int , pageSize: Int): LiveData<Resource<List<Product>>> {
        fetchProducts(pageNum , pageSize)
        return products
    }

}


