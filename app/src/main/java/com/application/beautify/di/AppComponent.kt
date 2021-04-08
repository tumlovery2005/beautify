package com.application.beautify.di

import com.application.beautify.di.module.PresentationModule
import com.application.beautify.model.Booking
import com.application.beautify.presenter.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {

    fun loginPresenter(): LoginPresenter

    fun beautifyPresenter(): BeautifyPresenter

    fun userPresenter(): UserPresenter

    fun ownerManagePresenter(): OwnerManagePresenter

    fun ownerQueuePresenter(): OwnerQueuePresenter

    fun ownerPromotionPresenter(): OwnerPromotionPresenter

    fun userHomePresenter(): UserHomePresenter

    fun userSearchPresenter(): UserSearchPresenter

    fun detailPresenter(): DetailPresenter

    fun bookingPresenter(): BookingPresenter
}