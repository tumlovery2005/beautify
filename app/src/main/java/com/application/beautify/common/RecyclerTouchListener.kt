package com.application.beautify.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.GestureDetector
import com.application.beautify.common.RecyclerViewListener

/**
 * Created by GuGolf on 11/4/2019 AD.
 */
class RecyclerTouchListener(context: Context, recycleView: RecyclerView, clicklistener: RecyclerViewListener): RecyclerView.OnItemTouchListener {

    private var clicklistener: RecyclerViewListener? = null
    private var gestureDetector: GestureDetector? = null

    init {
        this.clicklistener = clicklistener
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recycleView.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clicklistener != null && gestureDetector!!.onTouchEvent(e)) {
            clicklistener!!.onClick(child, rv.getChildAdapterPosition(child))
        }

        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}