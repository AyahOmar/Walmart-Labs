package com.aya.walmarttask.ui.main.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aya.walmarttask.R
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.databinding.ItemLayoutBinding
import com.aya.walmarttask.ui.main.view.ProductDetails
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class MainAdapter(private val product: ArrayList<Product>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(val itemBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(
        itemBinding.root
    ) {


        fun bind(product: Product) {
            itemView.apply {

                //View Binding
                itemBinding.productName.text = product.productName
                itemBinding.productPrice.text = product.price

                if (product.reviewCount!! > 0) {
                    itemBinding.productRatingText.visibility = View.VISIBLE
                    itemBinding.productRatingText.text = context.getString(
                        R.string.rating_text,
                        product.reviewCount
                    )
                    itemBinding.productRating.visibility = View.VISIBLE
                    itemBinding.productRating.numStars = 5
                    itemBinding.productRating.rating = product.reviewRating!!
                } else {
                    itemBinding.productRatingText.visibility = View.GONE
                    itemBinding.productRating.visibility = View.GONE
                }

                if (product.inStock == true) {
                    itemBinding.productInStock.setText(R.string.in_stock)
                    itemBinding.productInStock.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPositive
                        )
                    )
                } else {
                    itemBinding.productInStock.setText(R.string.out_of_stock)
                    itemBinding.productInStock.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorNegative
                        )
                    )
                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    itemBinding.productImage.transitionName = product.productId
                }
                Picasso.get().load("https://mobile-tha-server.firebaseapp.com/" + product.productImage).into(
                    itemBinding.productImage
                )

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  DataViewHolder {
        return DataViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    }

    override fun getItemCount(): Int = product.size


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(product[position])
        holder.itemView.setOnClickListener {
            var context = holder.itemView.context
            val intent = Intent(context, ProductDetails::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (context as Activity?)!!,
                holder.itemBinding.productImage, "productImage"
            )
            context.startActivity(intent, options.toBundle())


            val sharedPref = context?.getSharedPreferences("ProductDetails", Context.MODE_PRIVATE)
            val prefsEditor = sharedPref?.edit()
            val gson = Gson()
            val json = gson.toJson(product)
            prefsEditor?.putString("ProductDetailsObject", json)
            prefsEditor?.putInt("Position", position)
            prefsEditor?.apply()

        }
    }



    fun addProducts(product: List<Product>) {

        this.product.apply {
           // clear()
            addAll(product)


        }

    }
}