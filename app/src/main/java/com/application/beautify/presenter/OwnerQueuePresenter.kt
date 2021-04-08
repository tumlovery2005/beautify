package com.application.beautify.presenter

import com.application.beautify.ui.owner.queue.OwnerQueueView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface OwnerQueuePresenter: BasePresenter<OwnerQueueView> {

    fun onGetQueue(uid: String)

    fun onDeleteQueue(uid: String)
}