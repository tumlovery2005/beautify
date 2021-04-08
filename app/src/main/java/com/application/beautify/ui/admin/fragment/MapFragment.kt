package com.application.beautify.ui.admin.fragment

import android.app.AlertDialog
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
import com.application.beautify.common.*
import com.application.beautify.model.Beautify
import com.application.beautify.model.User
import com.application.beautify.ui.admin.adapter.BeautifyAdapter
import com.application.beautify.ui.admin.adapter.BeautifyLocationAdapter
import kotlinx.android.synthetic.main.activity_owner_promotion.*
import kotlinx.android.synthetic.main.fragment_beautify.view.*

class MapFragment : Fragment(), BeautifyView {

    private val presenter by lazy { beautifyPresenter() }
    private lateinit var mView: View
    private lateinit var adapter: BeautifyLocationAdapter

    private var list: ArrayList<Beautify> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_beautify, container, false)
        mView = view
        presenter.setView(this)

        initUI()

        return view
    }

    private fun initUI() {
        adapter = BeautifyLocationAdapter(arrayListOf())
        mView.recyclerViewBeautify.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewBeautify.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewBeautify.adapter = adapter
        mView.recyclerViewBeautify.addOnItemTouchListener(RecyclerTouchListener(context!!,  mView.recyclerViewBeautify, object: RecyclerViewListener {
            override fun onClick(view: View, position: Int) {
                StoredValue(context!!).saveObject(Constant().PREFS_BEAUTIFY_KEY, list[position])
                val dialog: DialogFragment = EditLocationFragment()
                dialog.show(fragmentManager, "dialog")
            }

            override fun onLongClick(view: View, position: Int) {
//                val promotion = adapter.getPromotion(position)
//                val builder = AlertDialog.Builder(context)
//                builder.setTitle("ยืนยัน")
//                builder.setMessage("คุณต้องการลบโปรโมชันนี้ออกใช่หรือไม่")
//                builder.setPositiveButton("ใช่") { dialog, _ ->
//                    presenter.onDeletePromotion(promotion.uid)
//                    dialog.dismiss()
//                }
//                builder.setNegativeButton("ไม่") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                val dialog = builder.create()
//                dialog.show()
            }
        }))

        mView.btnAddBeautify.hide()

        presenter.onGetBeautify()
    }

    override fun getBeautifySuccess(beautify: Beautify) {
        list.add(beautify)
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
