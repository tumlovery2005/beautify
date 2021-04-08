package com.application.beautify.ui.admin.adapter

import android.app.AlertDialog
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.model.User
import com.application.beautify.ui.admin.fragment.AddBeautifyFragment
import com.application.beautify.ui.admin.fragment.EditUserFragment
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class UserAdapter(var list: ArrayList<User>): RecyclerView.Adapter<UserHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent

        val view = inflater.inflate(R.layout.item_user, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.btnDeleteUser.onClick {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("ยืนยัน")
            builder.setMessage("คุณต้องการลบผู้ใช้ ${list[position].username} นี้ออกใช่หรือไม่")
            builder.setPositiveButton("ใช่") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setNegativeButton("ไม่") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
        holder.itemView.btnEditUser.onClick {
            val user = list[position]
            StoredValue(holder.itemView.context).saveObject(Constant().PREFS_USERS_KEY, user)
            val dialog: DialogFragment = EditUserFragment()
            val fragmentActivity = holder.itemView.context as FragmentActivity
            dialog.show(fragmentActivity.supportFragmentManager, "dialog")
        }
    }

    fun addUser(user: User) {
        list.add(user)
        notifyDataSetChanged()
    }
}

class UserHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        itemView.textViewItemType.text = getType(user.type)
        itemView.textViewItemUsername.text = user.username
        itemView.textViewItemEmail.text = user.email
    }

    private fun getType(type: Int): String {
        return if (type == 0) {
            "ผู้ใช้ทั่วไป"
        } else if (type == 1) {
            "เจ้าของร้าน"
        } else {
            "ผู้ดูแลระบบ"
        }
    }
}
