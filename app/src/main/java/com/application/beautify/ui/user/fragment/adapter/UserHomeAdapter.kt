package com.application.beautify.ui.user.fragment.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.beautify.R
import com.application.beautify.model.Promotion
import kotlinx.android.synthetic.main.item_promotion.view.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class UserHomeAdapter(var list: ArrayList<Promotion>): RecyclerView.Adapter<PromotionHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent

        val view = inflater.inflate(R.layout.item_promotion, parent, false)
        return PromotionHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PromotionHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addPromotion(promotion: Promotion) {
        list.add(promotion)
        notifyDataSetChanged()
    }

    fun getPromotion(position: Int): Promotion {
        return list[position]
    }
}

class PromotionHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(promotion: Promotion) {
        val byteArray = Base64.decode(promotion.image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        itemView.imageViewPromotion.setImageBitmap(bitmap)
        itemView.textViewPromotionStoreName.text = promotion.storeName
        itemView.textViewPromotionDetail.text = promotion.detail
    }
}
