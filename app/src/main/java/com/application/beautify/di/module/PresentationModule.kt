package com.application.beautify.di.module

import com.application.beautify.presenter.*
import com.application.beautify.presenter.implementation.*
import dagger.Binds
import dagger.Module

@Module(includes = [InteractionModule::class])
abstract class PresentationModule {

    @Binds
    abstract fun loginPresenter(loginPresenter: LoginPresenterImpl): LoginPresenter

    @Binds
    abstract fun beautifyPresenter(beautifyPresenter: BeautifyPresenterImpl): BeautifyPresenter

    @Binds
    abstract fun userPresenter(userPresenter: UserPresenterImpl): UserPresenter

    @Binds
    abstract fun ownerManagePresenter(ownerManagePresenter: OwnerManagePresenterImpl): OwnerManagePresenter

    @Binds
    abstract fun ownerQueuePresenter(ownerQueuePresenter: OwnerQueuePresenterImpl): OwnerQueuePresenter

    @Binds
    abstract fun ownerPromotionPresenter(ownerPromotionPresenter: OwnerPromotionPresenterImpl): OwnerPromotionPresenter

    @Binds
    abstract fun userHomePresenter(userHomePresenter: UserHomePresenterImpl): UserHomePresenter

    @Binds
    abstract fun userSearchPresenter(userSearchPresenter: UserSearchPresenterImpl): UserSearchPresenter

    @Binds
    abstract fun detailPresenter(detailPresenter: DetailPresenterImpl): DetailPresenter

    @Binds
    abstract fun bookingPresenter(bookingPresenter: BookingPresenterImpl): BookingPresenter
}