package com.franciscostanleyvasconceloszelaya.snapshots

import android.annotation.SuppressLint
import android.app.Application
import com.google.firebase.auth.FirebaseUser

@SuppressLint("Registered")
class SnapshotsApplication : Application() {
    companion object{
        const val PATH_SNAPSHOTS = "snapshots"
        const val PROPERTY_LIKE_LIST = "likelist"

        lateinit var currentUser: FirebaseUser
    }
}