package com.application.beautify.ui.owner.queue

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.RecyclerTouchListener
import com.application.beautify.common.RecyclerViewListener
import com.application.beautify.common.StoredValue
import com.application.beautify.model.Booking
import com.application.beautify.model.User
import com.application.beautify.ownerQueuePresenter
import com.application.beautify.ui.owner.queue.adapter.OwnerQueueAdapter
import kotlinx.android.synthetic.main.activity_owner_queue.*

class OwnerQueueActivity : AppCompatActivity(), OwnerQueueView {

    private val presenter by lazy { ownerQueuePresenter() }

    private lateinit var user: User
    private lateinit var adapter: OwnerQueueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_queue)
        title = "รายการคิวที่มีการจอง"
        presenter.setView(this)

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        initUI()
    }

    private fun initUI() {
        adapter = OwnerQueueAdapter(arrayListOf())
        recyclerViewQueue.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewQueue.itemAnimator = DefaultItemAnimator()
        recyclerViewQueue.adapter = adapter

        recyclerViewQueue.addOnItemTouchListener(RecyclerTouchListener(this, recyclerViewQueue, object: RecyclerViewListener {
            override fun onClick(view: View, position: Int) {

            }

            override fun onLongClick(view: View, position: Int) {
                val booking = adapter.getBookingList(position)
                val builder = AlertDialog.Builder(this@OwnerQueueActivity)
                builder.setTitle("ยืนยัน")
                builder.setMessage("คุณต้องการลบการจองของ ${booking.name} ออกใช่หรือไม่")
                builder.setPositiveButton("ใช่") { dialog, _ ->
                    presenter.onDeleteQueue(booking.uid)
                    dialog.dismiss()
                }
                builder.setNegativeButton("ไม่") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }))

        presenter.onGetQueue(user.store)
    }

    override fun onGetQueueSuccess(booking: Booking) {
        adapter.addQueue(booking)
    }

    override fun onDeleteSuccess() {
        adapter = OwnerQueueAdapter(arrayListOf())
        recyclerViewQueue.adapter = adapter
        presenter.onGetQueue(user.store)
    }

    override fun onError() {
        Toast.makeText(applicationContext, "เกิดข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}
