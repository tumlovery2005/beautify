package com.application.beautify.ui.user.fragment


import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.application.beautify.R
import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion
import com.application.beautify.ui.user.fragment.adapter.UserHomeAdapter
import com.application.beautify.userHomePresenter
import kotlinx.android.synthetic.main.fragment_user_home.view.*

class UserHomeFragment : Fragment(), UserHomeView {

    private val presenter by lazy { userHomePresenter() }

    private lateinit var mView: View
    private lateinit var adapter: UserHomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_user_home, container, false)
        mView = view
        presenter.setView(this)

        initUI()

        return view
    }

    private fun initUI() {
        adapter = UserHomeAdapter(arrayListOf())
        mView.recyclerViewPromotion.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewPromotion.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewPromotion.adapter = adapter

        presenter.onGetSuggestBeautify()
        presenter.onGetPromotion()
    }

    override fun onGetSuggestBeautifySuccess(beautify: Beautify) {
        val byteArray = Base64.decode(beautify.images[0], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        mView.imageViewSuggestBeautify.setImageBitmap(bitmap)
        mView.textViewSuggestBeautifyName.text = beautify.name
        mView.textViewSuggestBeautifyDetail.text = beautify.detail
    }

    override fun onGetPromotionsSuccess(promotion: Promotion) {
        adapter.addPromotion(promotion)
    }

    override fun onGetDataError() {
        Toast.makeText(context, "เกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }

}
