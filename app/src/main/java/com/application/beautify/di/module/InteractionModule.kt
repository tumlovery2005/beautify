package com.application.beautify.di.module

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.authentication.FirebaseAuthenticationManager
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.firebase.database.FirebaseDatabaseManager
import com.application.beautify.firebase.storage.FirebaseStorageInterface
import com.application.beautify.firebase.storage.FirebaseStorageManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by GuGolf on 26/2/2019 AD.
 */
@Module(includes = [FirebaseModule::class])
@Singleton
abstract class InteractionModule {

    @Binds
    abstract fun authentication(authentication: FirebaseAuthenticationManager): FirebaseAuthenticationInterface

    @Binds
    abstract fun database(database: FirebaseDatabaseManager): FirebaseDatabaseInterface

    @Binds
    abstract fun storage(storage: FirebaseStorageManager): FirebaseStorageInterface
}