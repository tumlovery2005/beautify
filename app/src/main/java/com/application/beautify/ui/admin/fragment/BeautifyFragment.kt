package com.application.beautify.ui.admin.fragment

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
import com.application.beautify.model.User
import com.application.beautify.ui.admin.adapter.BeautifyAdapter
import kotlinx.android.synthetic.main.fragment_beautify.view.*

class BeautifyFragment : Fragment(), BeautifyView {

    private val presenter by lazy { beautifyPresenter() }
    private lateinit var mView: View
    private lateinit var adapter: BeautifyAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_beautify, container, false)
        mView = view
        presenter.setView(this)

        initUI()

        return view
    }

    private fun initUI() {
        adapter = BeautifyAdapter(arrayListOf())
        mView.recyclerViewBeautify.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewBeautify.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewBeautify.adapter = adapter

        mView.btnAddBeautify.onClick {
            val dialog: DialogFragment = AddBeautifyFragment()
            dialog.show(fragmentManager, "dialog")
        }

        presenter.onGetBeautify()
    }

    override fun getBeautifySuccess(beautify: Beautify) {
        adapter.addBeautify(beautify)
    }

    override fun addBeautifySuccess() {

    }

    override fun getUserSuccess(user: User) {

    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
