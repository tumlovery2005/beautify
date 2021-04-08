package com.application.beautify.ui.owner.promotion

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.*
import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion
import com.application.beautify.model.User
import com.application.beautify.ownerPromotionPresenter
import com.application.beautify.ui.owner.promotion.adapter.OwnerPromotionAdapter
import kotlinx.android.synthetic.main.activity_owner_promotion.*

class OwnerPromotionActivity : AppCompatActivity(), OwnerPromotionView {

    private val presenter by lazy { ownerPromotionPresenter() }

    private lateinit var user: User
    private lateinit var adapter: OwnerPromotionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_promotion)
        title = "จัดการโปรโมชัน"
        presenter.setView(this)

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        initUI()
    }

    private fun initUI() {
        adapter = OwnerPromotionAdapter(arrayListOf())
        recyclerViewPromotion.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewPromotion.itemAnimator = DefaultItemAnimator()
        recyclerViewPromotion.adapter = adapter

        recyclerViewPromotion.addOnItemTouchListener(RecyclerTouchListener(this, recyclerViewPromotion, object: RecyclerViewListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View, position: Int) {
                val promotion = adapter.getPromotion(position)
                val builder = AlertDialog.Builder(this@OwnerPromotionActivity)
                builder.setTitle("ยืนยัน")
                builder.setMessage("คุณต้องการลบโปรโมชันนี้ออกใช่หรือไม่")
                builder.setPositiveButton("ใช่") { dialog, _ ->
                    presenter.onDeletePromotion(promotion.uid)
                    dialog.dismiss()
                }
                builder.setNegativeButton("ไม่") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }))

        btnAddPromotion.onClick {
            startActivity(Intent(this, AddPromotionActivity::class.java))
        }

        presenter.onGetPromotion(user.store)
    }

    override fun onGetPromotionSuccess(promotion: Promotion) {
        adapter.addPromotion(promotion)
    }

    override fun onGetBeautify(beautify: Beautify) {

    }

    override fun onAddPromotionSuccess() {

    }

    override fun onDeleteSuccess() {
        adapter = OwnerPromotionAdapter(arrayListOf())
        recyclerViewPromotion.adapter = adapter
        presenter.onGetPromotion(user.store)
    }

    override fun onError() {
        Toast.makeText(applicationContext, "เกิดข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}
