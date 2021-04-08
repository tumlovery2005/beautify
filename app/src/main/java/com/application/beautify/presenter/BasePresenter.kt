package com.application.beautify.presenter

interface BasePresenter<in T> {

    fun setView(view: T)
}