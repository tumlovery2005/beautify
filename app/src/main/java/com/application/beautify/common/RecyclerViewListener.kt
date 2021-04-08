package com.application.beautify.common

import android.view.View

/**
 * Created by GuGolf on 11/4/2019 AD.
 */
interface RecyclerViewListener {

    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)
}