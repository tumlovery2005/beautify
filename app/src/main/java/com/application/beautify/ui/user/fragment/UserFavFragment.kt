package com.application.beautify.ui.user.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.application.beautify.R
import com.application.beautify.common.*
import com.application.beautify.detailPresenter
import com.application.beautify.model.Favorite
import com.application.beautify.model.User
import com.application.beautify.ui.admin.adapter.BeautifyAdapter
import com.application.beautify.ui.login.LoginActivity
import com.application.beautify.ui.register.RegisterActivity
import com.application.beautify.ui.user.DetailActivity
import com.application.beautify.ui.user.DetailView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user_fav.view.*

class UserFavFragment : Fragment(), DetailView {

    private val presenter by lazy { detailPresenter() }

    private lateinit var mView: View
    private lateinit var user: User

    private lateinit var adapter: BeautifyAdapter
    private var list: ArrayList<Favorite> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_fav, container, false)
        presenter.setView(this)

        mView = view

        initUI()

        return view
    }

    override fun onResume() {
        super.onResume()
        val count = list.size
        activity!!.title = "รายการโปรด ($count)"
    }

    private fun initUI() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            mView.cardViewFav.visibility = View.GONE
            mView.recyclerViewFav.visibility = View.VISIBLE

            user = StoredValue(context!!).getObject(Constant().PREFS_USER_KEY) as User

            presenter.getFav(user.uid)
        } else {
            mView.cardViewFav.visibility = View.VISIBLE
            mView.recyclerViewFav.visibility = View.GONE
        }

        mView.btnFavLogin.onClick {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        mView.btnFavRegister.onClick {
            startActivity(Intent(context, RegisterActivity::class.java))
        }

        adapter = BeautifyAdapter(arrayListOf())
        mView.recyclerViewFav.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewFav.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewFav.adapter = adapter
        mView.recyclerViewFav.addOnItemTouchListener(RecyclerTouchListener(context!!, mView.recyclerViewFav, object: RecyclerViewListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, DetailActivity::class.java)
                StoredValue(context!!).saveObject(Constant().PREFS_ITEM_KEY, list[position].beautify.mapToFull())
                startActivity(intent)
            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))
    }

    override fun addFavoriteSuccess() {

    }

    override fun onGetFavorite(favorite: Favorite) {
        list.add(favorite)
        adapter.addBeautify(favorite.beautify)
        val count = list.size
        activity!!.title = "รายการโปรด ($count)"
    }

    override fun onError() {

    }

}
