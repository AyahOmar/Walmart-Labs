package com.aya.walmarttask.ui.main.adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.aya.walmarttask.R
import com.aya.walmarttask.data.api.RetrofitBuilder
import com.aya.walmarttask.data.model.Product
import com.aya.walmarttask.databinding.ItemProductDetailsBinding
import com.squareup.picasso.Picasso


class ProductDetailsAdapter(private val product: ArrayList<Product>) : RecyclerView.Adapter<ProductDetailsAdapter.DataViewHolder>()  {

    class DataViewHolder(private val itemBinding: ItemProductDetailsBinding) : RecyclerView.ViewHolder(
        itemBinding.root
    ) {
    fun bind(product: Product) {
        itemView.apply {

            //View Binding
            itemBinding.productName.text = product.productName
            itemBinding.productPrice.text = product.price


            // Description
            if(product.shortDescription!=null){
                val shortDescHtml = HtmlCompat.fromHtml(
                    product.shortDescription, HtmlCompat.FROM_HTML_MODE_LEGACY
                ).trim()
                itemBinding.productShortDescription.text = SpannableString(shortDescHtml)
            }
            if(product.longDescription!=null) {
                val longDescHtml = HtmlCompat.fromHtml(
                    product.longDescription, HtmlCompat.FROM_HTML_MODE_LEGACY
                ).trim()
                val longDesc = SpannableString(longDescHtml)
                val longDescVis = if (longDesc.isEmpty()) {
                    View.GONE
                } else {
                    itemBinding.productLongDescription.text = longDesc
                    View.VISIBLE
                }
                itemBinding.productLongDescription.visibility = longDescVis
                itemBinding.aboutLabel.visibility = longDescVis
            }

            //Rating
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

            // Add item to cart

            itemBinding.addToCard.setOnClickListener {
                // Delegate click here
                Toast.makeText(context, R.string.added_to_card, Toast.LENGTH_LONG).show()
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                itemBinding.productImage.transitionName = product.productId
            }
            Picasso.get().load(RetrofitBuilder.BASE_URL + product.productImage).into(itemBinding.productImage)

        }
    }
}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemProductDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = product.size
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(product[position])
    }

    fun addProducts(product: List<Product>) {
        this.product.apply {
            clear()
            addAll(product)
        }

    }


}