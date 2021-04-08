package com.application.beautify.ui.admin.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.application.beautify.R
import com.application.beautify.beautifyPresenter
import com.application.beautify.common.onClick
import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion
import com.application.beautify.model.User
import com.application.beautify.ui.admin.adapter.BeautifyAdapter
import com.application.beautify.ui.owner.promotion.AddPromotionActivity
import com.application.beautify.ui.user.fragment.UserHomeView
import com.application.beautify.ui.user.fragment.adapter.UserHomeAdapter
import com.application.beautify.userHomePresenter
import kotlinx.android.synthetic.main.fragment_beautify.view.*

class PromotionFragment : Fragment(), UserHomeView {

    private val presenter by lazy { userHomePresenter() }
    private lateinit var mView: View
    private lateinit var adapter: UserHomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_beautify, container, false)
        mView = view
        presenter.setView(this)

        initUI()

        return view
    }

    private fun initUI() {
        adapter = UserHomeAdapter(arrayListOf())
        mView.recyclerViewBeautify.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewBeautify.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewBeautify.adapter = adapter

        mView.btnAddBeautify.onClick {
            startActivity(Intent(context, AddPromotionActivity::class.java))
        }

        presenter.onGetPromotion()
    }

    override fun onGetSuggestBeautifySuccess(beautify: Beautify) {

    }

    override fun onGetPromotionsSuccess(promotion: Promotion) {
        adapter.addPromotion(promotion)
    }

    override fun onGetDataError() {
        Toast.makeText(context, "เกิดข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}
